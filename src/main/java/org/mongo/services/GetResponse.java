package org.mongo.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
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
        MongoCursor<Document> cursor = collection.find().projection(projection).cursor();
        while (cursor.hasNext()) {
            Document doc = collection.find().projection(projection).cursor().next();
            doc.forEach((a, b) -> {
                System.out.println(a + "\t" + b);
            });
            cursor.next();
        }

//        searchObj = new BasicDBObject();
//        searchObj.append("last_name","Fomina");
    }
}
