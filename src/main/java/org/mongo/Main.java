package org.mongo;

import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase db = mongoClient.getDatabase("db1");
        MongoCollection<Document> collection = db.getCollection("mycol");

        //добавление
//     Document newDoc = new Document("name","Adr").
//             append("age",45).
//             append("prof",new Document("title","Dev").append("description","test description"));

//     Document newDoc =getDocument("Elena","Burlakova","eburlakova@ya.ru",
//             "898197898",LocalDate.of(1978,10,9),"it_prog",120000.0);
////        Document newDoc =getDocument("Tatyana","Fomina","tfomina@ya.ru",
////                "989841665",LocalDate.of(1988,7,15),"arch",87000.0);
//
//     collection.insertOne(newDoc);

//        //изменение
//        BasicDBObject searchObj = new BasicDBObject();
//        searchObj.append("first_name", "Milena");
//        BasicDBObject changeObj = new BasicDBObject();
//        changeObj.append("$set", new BasicDBObject().append("first_name", "Alena"));
//        collection.updateOne(searchObj, changeObj);

        //удаление
//        searchObj = new BasicDBObject();
//        searchObj.append("name", "Adr");
//        collection.deleteOne(searchObj);

        //выборка
//        searchObj = new BasicDBObject();
//        searchObj.append("last_name","Fomina");
        //в find можно послать searchObj

        //удаление таблицы по идентификатору
//        сollection.deleteOne(new Document("_id", new ObjectId("65a046c9f60514390621a961")));
        String df = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(df);

        Block<Document> printBlock = System.out::println;
        Consumer<Document> printBlock2 = (a -> {
            System.out.println(a.get("first_name") + "\t" + a.get("phone_number"));
        });
        Consumer<Document> printBlock3 = (a -> {
            System.out.println(a.get("_id") + "\t"+"средняя зарплата" +"\t"+ a.get("avg_salary"));
        });
        Consumer<Document> printAllBlock = (a -> {
            if (a.containsKey("birth_date"))
                System.out.println(a.get("first_name") + "\t" + a.get("last_name") + "\t" + a.get("email") + "\t"
                        + a.get("phone_number") + "\t" + sdf.format(a.getDate("birth_date")) + "\t" + a.get("job_id") + "\t" +
                        a.get("salary"));
        });

        System.out.println("Вывод всех данных");
//        1.Напишите запрос MongoDB для отображения всех данных из представленной таблицы
        collection.find().forEach(printBlock);

//        2.Напишите запрос MongoDB для отображения ФИО и даты рождения всех лиц
//        из представленной таблицы
        System.out.println("\n Вывод ФИО и дат рождения всех");
        for (Document doc : collection.find()) {
            if (doc.containsKey("birth_date"))
                System.out.println(doc.get("first_name") + "\t" + (doc.get("last_name") +
                        "\t" + sdf.format(doc.getDate("birth_date"))));
        }
//    3.Напишите запрос MongoDB для отображения всех работников, сортируя
//        их в порядке уменьшения заработной платы
        //Сортировка по полям
        System.out.println("\n Сортировка по зп в порядке уменьшения");
        Bson sort = Aggregates.sort(Sorts.descending("salary"));
        collection.aggregate(List.of(sort)).forEach(printAllBlock);

//        4.Напишите запрос MongoDB для отображения средней зарплаты всех работников

        //группировка c аккумулированием
        System.out.println("\n Подсчет средней зарплаты всех работников");
        Bson group=Aggregates.group(null,
                Accumulators.avg("avg_salary","$"+"salary"));
        collection.aggregate(List.of(group)).forEach(printBlock3);
        System.out.println("\n Подсчет средней зарплаты по группам");
       group=Aggregates.group("$job_id",
                Accumulators.avg("avg_salary","$"+"salary"));
        collection.aggregate(List.of(group)).forEach(printBlock3);

//    5.Напишите запрос MongoDB для отображения только имени и номера телефона сотрудников
//        из представленной таблицы
        System.out.println("\n Вывод имени и номера телефона");
        collection.find().forEach(printBlock2);

        //6.выборка по условию (дополнительное задание)
        System.out.println("\n выборка по полю job_id ='arch");
        Bson filter =Aggregates.match(Filters.eq("job_id", "arch"));
        collection.aggregate(List.of(filter)).forEach(printAllBlock);

        //счетчик
        Document doc = collection.aggregate(List.of(filter,Aggregates.count())).first();
        assert doc != null;
        System.out.println("\n Количество записей 'arch'" + doc.get("count"));

//        7.Выборка с ограничением записей
        System.out.println("\n Выборка с ограничением записей до 1");
        Bson limit = Aggregates.limit(1);
        collection.aggregate(List.of(filter,limit)).forEach(printAllBlock);
    }

    //получить имена таблиц
    private static void getTableList(MongoDatabase database) {
        database.listCollectionNames()
                .forEach((Consumer<String>) System.out::println);
        // todo
        database.listCollections()
                .forEach((Consumer<Document>) System.out::println);
    }

    //получить имена БД
    private static void getDbList(MongoClient mongoClient) {
        mongoClient.listDatabaseNames()
                .forEach((Consumer<String>) System.out::println);
    }

    //получить имена колекций
    private static void getСol(MongoDatabase db) {
        db.listCollectionNames()
                .forEach((Consumer<String>) System.out::println);
    }


    private static Document getDocument(String fname, String lname, String email, String phnumber,
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
