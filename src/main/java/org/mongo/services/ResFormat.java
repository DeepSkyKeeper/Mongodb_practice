package org.mongo.services;

import com.mongodb.Block;
import org.bson.Document;

import java.text.SimpleDateFormat;

public class ResFormat {
    public static Block<Document> getPrintBlock() {
        return System.out::println;
    }

    public static Block<Document> getPrintBlock2() {
        return (a -> {
            System.out.println(a.get("first_name") + "\t" + a.get("phone_number"));
        });
    }

    public static Block<Document> getPrintBlock3() {
        return (a -> {
            System.out.println(a.get("_id") + "\t" + "средняя зарплата" + "\t" + a.get("avg_salary"));
        });
    }

    public static Block<Document> getPrintAllBlock() {
        return (a -> {
            if (a.containsKey("birth_date"))
                System.out.println(a.get("first_name") + "\t" + a.get("last_name") + "\t" + a.get("email") + "\t"
                        + a.get("phone_number") + "\t" + getDateFormat().format(a.getDate("birth_date")) + "\t" + a.get("job_id") + "\t" +
                        a.get("salary"));
        });
    }

    public static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");

    }
}
