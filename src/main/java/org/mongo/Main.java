package org.mongo;

import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongo.services.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        MongoCollection<Document> collection = MongoConnect.getCollection("myCol");

        /**
         * добавление
         */
//      var add =new AddToDB(collection);
//        add.data3();

/**
 *изменение
 */
//     var update = new Update(collection);
//     update.setObj();

        //удаление
//        new DeleteObj(collection,"name","Adr");

        //выборка

        //в find можно послать searchObj

        //удаление таблицы по идентификатору
//        сollection.deleteOne(new Document("_id", new ObjectId("65a046c9f60514390621a961")));

        System.out.println("Вывод всех данных");
//        1.Напишите запрос MongoDB для отображения всех данных из представленной таблицы
        collection.find().forEach(ResFormat.getPrintBlock());

//        2.Напишите запрос MongoDB для отображения ФИО и даты рождения всех лиц
//        из представленной таблицы
        System.out.println("\n Вывод ФИО и дат рождения всех");
        for (Document doc : collection.find()) {
            if (doc.containsKey("birth_date"))
                System.out.println(doc.get("first_name") + "\t" + (doc.get("last_name") +
                        "\t" + ResFormat.getDateFormat().format(doc.getDate("birth_date"))));
        }
//    3.Напишите запрос MongoDB для отображения всех работников, сортируя
//        их в порядке уменьшения заработной платы
        //Сортировка по полям
        System.out.println("\n Сортировка по зп в порядке уменьшения");
        Bson sort = Aggregates.sort(Sorts.descending("salary"));
        collection.aggregate(List.of(sort)).forEach(ResFormat.getPrintAllBlock());

//        4.Напишите запрос MongoDB для отображения средней зарплаты всех работников

        //группировка c аккумулированием
        System.out.println("\n Подсчет средней зарплаты всех работников");
        Bson group = Aggregates.group(null,
                Accumulators.avg("avg_salary", "$" + "salary"));
        collection.aggregate(List.of(group)).forEach(ResFormat.getPrintBlock3());
        System.out.println("\n Подсчет средней зарплаты по группам");
        group = Aggregates.group("$job_id",
                Accumulators.avg("avg_salary", "$" + "salary"));
        collection.aggregate(List.of(group)).forEach(ResFormat.getPrintBlock3());

//    5.Напишите запрос MongoDB для отображения только имени и номера телефона сотрудников
//        из представленной таблицы
        System.out.println("\n Вывод имени и номера телефона");
        new GetResponse(collection).getSomeFields("first_name", "phone_number");

        //6.выборка по условию (дополнительное задание)
        System.out.println("\n выборка по полю job_id ='arch");
        Bson filter = Aggregates.match(Filters.eq("job_id", "arch"));
        collection.aggregate(List.of(filter)).forEach(ResFormat.getPrintAllBlock());

        //счетчик
        Document doc = collection.aggregate(List.of(filter, Aggregates.count())).first();
        assert doc != null;
        System.out.println("\n Количество записей 'arch'" + doc.get("count"));

//        7.Выборка с ограничением записей
        System.out.println("\n Выборка с ограничением записей до 1");
        Bson limit = Aggregates.limit(1);
        collection.aggregate(List.of(filter, limit)).forEach(ResFormat.getPrintAllBlock());

    }

//    В методе getTableList, getDbList, getСol
//    вы вызываете метод forEach для печати, но это может быть проблематично,
//    если коллекции или базы данных очень большие.
//    Рекомендуется использовать цикл while с методом iterator
//    для обработки больших результатов.

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

}
