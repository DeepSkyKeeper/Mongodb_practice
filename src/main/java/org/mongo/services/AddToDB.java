package org.mongo.services;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Map;

public class AddToDB {
    final MongoCollection<Document> collection;
    Document newDoc;

    public AddToDB(MongoCollection<Document> collection) {
        this.collection = collection;

    }

    public void data1() {
        //Создать документ типа1
        newDoc = new Document("name", "Adr").
                append("age", 45).
                append("prof", new Document("title", "Dev").append("description", "test description"));
        collection.insertOne(newDoc);
    }

    public void data2() {
        newDoc = getDocument("Elena", "Burlakova", "eburlakova@ya.ru",
                "898197898", LocalDate.of(1978, 10, 9), "it_prog", 120000.0);
        collection.insertOne(newDoc);
    }

    public void data3() {
        newDoc = getDocument("Tatyana", "Fomina", "tfomina@ya.ru",
                "989841665", LocalDate.of(1988, 7, 15), "arch", 87000.0);
        collection.insertOne(newDoc);
    }

    private Document getDocument(String fname, String lname, String email, String phnumber,
                                 LocalDate bDate, String jobId, Double salary) {
        return new Document(Map.of(
                "id", new ObjectId(),
                "first_name", fname,
                "last_name", lname,
                "email", email,
                "phone_number", phnumber,
                "birth_date", bDate,
                "job_id", jobId,
                "salary", salary
        ));
    }
}
