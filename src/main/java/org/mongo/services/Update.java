package org.mongo.services;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Update {
    final MongoCollection<Document> collection;

    public Update(MongoCollection<Document> collection) {
        this.collection = collection;
        setObj();
    }

    public void setObj() {
        var searchObj = new BasicDBObject();
        searchObj.append("first_name", "Milena");
        BasicDBObject changeObj = new BasicDBObject();
        changeObj.append("$set", new BasicDBObject().append("first_name", "Alena"));
        collection.updateOne(searchObj, changeObj);
    }

}
