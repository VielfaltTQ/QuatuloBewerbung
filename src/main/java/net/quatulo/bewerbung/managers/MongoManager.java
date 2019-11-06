package net.quatulo.bewerbung.managers;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import net.quatulo.bewerbung.QuatuloBewerbung;
import org.bson.Document;

@Getter
@Setter
public class MongoManager {

    private QuatuloBewerbung instance;
    private String hostname;
    private Integer port;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> playerCollection;

    public MongoManager(QuatuloBewerbung instance, String hostname, Integer port) {

        setInstance(instance);
        setHostname(hostname);
        setPort(port);

    }

    public void connect() {

        setMongoClient(MongoClients.create(new ConnectionString("mongodb://" + this.hostname + ":" + this.port)));
        setMongoDatabase(getMongoClient().getDatabase("QuatuloBewerbung"));
        setPlayerCollection(getMongoDatabase().getCollection("players"));

    }

    public void connect(String username, String password, String database) {

        setMongoClient(MongoClients.create(new ConnectionString("mongodb://" + username + ":" + password + "@" + this.hostname + ":" + this.port + "/" + database)));
        setMongoDatabase(getMongoClient().getDatabase("QuatuloBewerbung"));
        setPlayerCollection(getMongoDatabase().getCollection("players"));

    }

}
