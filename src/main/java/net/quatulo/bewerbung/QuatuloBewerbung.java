package net.quatulo.bewerbung;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;
import net.quatulo.bewerbung.listeners.ChatListener;
import net.quatulo.bewerbung.listeners.LoginListener;
import net.quatulo.bewerbung.managers.MongoManager;
import net.quatulo.bewerbung.managers.RedisManager;

@Getter
@Setter
public class QuatuloBewerbung  extends Plugin {

    private static QuatuloBewerbung instance;
    private MongoManager mongoManager;
    private RedisManager redisManager;

    @Override
    public void onEnable() {

        instance = this;

        setMongoManager(new MongoManager(getInstance(), "127.0.0.1", 27017));
        getMongoManager().connect("## USERNAME ##", "## PASSWORD ##", "admin");
        setRedisManager(new RedisManager(getInstance(), "127.0.0.1", 6379, 5000));
        getRedisManager().connect("## PASSWORD ##");

        register();

    }

    private void register() {

        new LoginListener(getInstance());
        new ChatListener(getInstance());

    }

    public static QuatuloBewerbung getInstance() { return instance; }

}
