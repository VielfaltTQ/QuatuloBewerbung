package net.quatulo.bewerbung.listeners;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.quatulo.bewerbung.QuatuloBewerbung;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Random;

@Getter
@Setter
public class LoginListener implements Listener {

    private QuatuloBewerbung instance;

    public LoginListener(QuatuloBewerbung instance) {

        setInstance(instance);
        ProxyServer.getInstance().getPluginManager().registerListener(getInstance(), this);

    }

    @EventHandler
    public void onLogin(LoginEvent event) {

        String playername = event.getConnection().getName();
        String uniqueID = event.getConnection().getUniqueId().toString();

        getInstance().getMongoManager().getPlayerCollection().find(Filters.eq("UUID", uniqueID)).first((document, throwable) -> {

            if(document == null) {

                document = new Document();
                document.append("Name", playername);
                document.append("UUID", uniqueID);
                document.append("Messages", 0);

                getInstance().getMongoManager().getPlayerCollection().insertOne(document, (aVoid, throwable12) -> {

                    System.out.println("DOCUMENT FOR PLAYER ('" + playername + ":" + uniqueID + "') WAS CREATED!");

                });

            } else {

                if(!document.getString("Name").equalsIgnoreCase(playername)) {

                    document.replace("Name", playername);

                    getInstance().getMongoManager().getPlayerCollection().replaceOne(Filters.eq("UUID", uniqueID), document, (updateResult, throwable1) -> {

                        System.out.println("UPDATED NAME FOR PLAYER ('" + playername + ":" + uniqueID + "') AFTER NAME CHANGE!");

                    });

                }

            }

            getInstance().getRedisManager().getJedis().set("PLAYER_DOCUMENT:" + uniqueID, document.toJson());

        });

    }

}
