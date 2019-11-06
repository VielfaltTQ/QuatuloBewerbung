package net.quatulo.bewerbung.managers;

import net.quatulo.bewerbung.QuatuloBewerbung;
import redis.clients.jedis.Jedis;

public class RedisManager {

    private QuatuloBewerbung instance;
    private String hostname;
    private Integer port;
    private Integer timeout;
    private Jedis jedis;

    public RedisManager(QuatuloBewerbung instance, String hostname, Integer port, Integer timeout) {

        this.instance = instance;
        this.hostname = hostname;
        this.port = port;
        this.timeout = timeout;

    }

    public void connect() {

        this.jedis = new Jedis(this.hostname, this.port, this.timeout);
        this.jedis.connect();

    }

    public void connect(String password) {

        this.jedis = new Jedis(this.hostname, this.port, this.timeout);
        this.jedis.auth(password);
        this.jedis.connect();

    }

    private QuatuloBewerbung getInstance() { return this.instance; }
    public Jedis getJedis() { return this.jedis; }

}
