package org.mongo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

public class GetResponse {
    final MongoCollection<Document> collection;

    public GetResponse(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public void getSomeFields(String... fieldNames) {
        // Проекция для выбора конкретных полей
        Bson projection = Projections.fields(
                Projections.include(fieldNames),
                Projections.excludeId() // Исключение поля "_id"
        );

// Запрос с использованием проекции
        collection.find().projection(projection).forEach(ResFormat.getPrintAllBlock());

//        searchObj = new BasicDBObject();
//        searchObj.append("last_name","Fomina");
    }
}
