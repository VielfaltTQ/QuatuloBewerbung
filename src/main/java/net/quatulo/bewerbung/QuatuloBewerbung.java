package net.quatulo.bewerbung;

import net.md_5.bungee.api.plugin.Plugin;
import net.quatulo.bewerbung.listeners.ChatListener;
import net.quatulo.bewerbung.listeners.LoginListener;
import net.quatulo.bewerbung.managers.MongoManager;
import net.quatulo.bewerbung.managers.RedisManager;

public class QuatuloBewerbung  extends Plugin {

    private static QuatuloBewerbung instance;
    private MongoManager mongoManager;
    private RedisManager redisManager;

    @Override
    public void onEnable() {

        instance = this;

        this.mongoManager = new MongoManager(getInstance(), "127.0.0.1", 27017);
        this.mongoManager.connect("## USERNAME ##", "## PASSWORD ##", "admin");
        this.redisManager = new RedisManager(getInstance(), "127.0.0.1", 6379, 5000);
        this.redisManager.connect("## PASSWORD ##");

        register();

    }

    private void register() {

        new LoginListener(getInstance());
        new ChatListener(getInstance());

    }

    public static QuatuloBewerbung getInstance() { return instance; }
    public MongoManager getMongoManager() { return this.mongoManager; }
    public RedisManager getRedisManager() { return this.redisManager; }

}
