package net.quatulo.bewerbung.listeners;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.quatulo.bewerbung.QuatuloBewerbung;
import org.bson.Document;

import java.util.concurrent.Executors;
@Getter
@Setter
public class ChatListener implements Listener {

    private QuatuloBewerbung instance;

    public ChatListener(QuatuloBewerbung instance) {

        setInstance(instance);
        ProxyServer.getInstance().getPluginManager().registerListener(getInstance(), this);

    }

    @EventHandler
    public void onChat(ChatEvent event) {

        if(!(event.getSender() instanceof ProxiedPlayer)) { return; }

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
        String playername = proxiedPlayer.getName();
        String uniqueID = proxiedPlayer.getUniqueId().toString();

        if(getInstance().getRedisManager().getJedis().exists("PLAYER_DOCUMENT:" + uniqueID)) {

            Document document = Document.parse(getInstance().getRedisManager().getJedis().get("PLAYER_DOCUMENT:" + uniqueID));
            document.replace("Messages", (document.getInteger("Messages") + 1));

            getInstance().getMongoManager().getPlayerCollection().replaceOne(Filters.eq("UUID", uniqueID), document, (updateResult, throwable) -> {

                System.out.println("UPDATED MESSAGES FOR PLAYER ('" + playername + ":" + uniqueID + "') AFTER WRITTEN MESSAGE!");

            });

            getInstance().getRedisManager().getJedis().set("PLAYER_DOCUMENT:" + uniqueID, document.toJson());

        }

    }

}
