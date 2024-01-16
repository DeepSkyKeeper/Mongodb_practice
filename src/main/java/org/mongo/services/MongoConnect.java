package org.mongo.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConnect {
    public static MongoCollection<Document> getCollection(String colName) {

        MongoDatabase db;
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            db = mongoClient.getDatabase("db1");
        }
        return db.getCollection(colName);
    }
}
