package org.mongo.services;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class DeleteObj {
    final MongoCollection<Document> collection;

    public DeleteObj(MongoCollection<Document> collection, String key, Object val) {
        this.collection = collection;
    }

    private void setObj() {
        var searchObj = new BasicDBObject();
        searchObj.append("name", "Adr");
        collection.deleteOne(searchObj);
    }
}
