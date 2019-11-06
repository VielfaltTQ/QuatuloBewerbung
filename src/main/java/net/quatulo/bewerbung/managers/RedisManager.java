package net.quatulo.bewerbung.managers;

import lombok.Getter;
import lombok.Setter;
import net.quatulo.bewerbung.QuatuloBewerbung;
import redis.clients.jedis.Jedis;

@Getter
@Setter
public class RedisManager {

    private QuatuloBewerbung instance;
    private String hostname;
    private Integer port;
    private Integer timeout;
    private Jedis jedis;

    public RedisManager(QuatuloBewerbung instance, String hostname, Integer port, Integer timeout) {

        setInstance(instance);
        setHostname(hostname);
        setPort(port);
        setTimeout(timeout);

    }

    public void connect() {

        setJedis(new Jedis(getHostname(), getPort(), getTimeout()));
        getJedis().connect();

    }

    public void connect(String password) {

        setJedis(new Jedis(getHostname(), getPort(), getTimeout()));
        getJedis().auth(password);
        getJedis().connect();

    }

}
