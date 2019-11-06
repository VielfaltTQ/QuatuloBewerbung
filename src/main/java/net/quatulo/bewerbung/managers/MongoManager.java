package net.quatulo.bewerbung.managers;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import net.quatulo.bewerbung.QuatuloBewerbung;
import org.bson.Document;

public class MongoManager {

    private QuatuloBewerbung instance;
    private String hostname;
    private Integer port;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> playerCollection;

    public MongoManager(QuatuloBewerbung instance, String hostname, Integer port) {

        this.instance = instance;
        this.hostname = hostname;
        this.port = port;

    }

    public void connect() {

        this.mongoClient = MongoClients.create(new ConnectionString("mongodb://" + this.hostname + ":" + this.port));
        this.mongoDatabase = this.mongoClient.getDatabase("QuatuloBewerbung");
        this.playerCollection = this.mongoDatabase.getCollection("players");

    }

    public void connect(String username, String password, String database) {

        this.mongoClient = MongoClients.create(new ConnectionString("mongodb://" + username + ":" + password + "@" + this.hostname + ":" + this.port + "/" + database));
        this.mongoDatabase = this.mongoClient.getDatabase("QuatuloBewerbung");
        this.playerCollection = this.mongoDatabase.getCollection("players");

    }

    private QuatuloBewerbung getInstance() { return this.instance; }
    public MongoCollection<Document> getPlayerCollection() { return this.playerCollection; }

}
