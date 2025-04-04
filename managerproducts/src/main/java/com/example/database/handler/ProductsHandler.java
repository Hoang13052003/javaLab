package com.example.database.handler;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.example.database.DatabaseConnection;
import com.example.interfaces.DatabaseHandler;
import com.example.models.DataExemple;
import com.example.models.Products;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.example.models.Status;

public class ProductsHandler implements DatabaseHandler {

    private MongoCollection<Document> collection;

    public ProductsHandler() {
        MongoDatabase db = DatabaseConnection.getInstance().getDatabase();
        this.collection = db.getCollection("Products");
    }

    @Override
    public List<Object> getData() {
        List<Object> lisObjects = new ArrayList<>();

        for (Document doc : this.collection.find()) {

            try {
                LocalDate date = LocalDate.parse(doc.getString("expired_date"), DataExemple.DATE_FORMATTER);
                Status status = statusExpired(date);

                Products item = new Products(
                        doc.getInteger("code"),
                        doc.getString("name"),
                        doc.getDouble("price"),
                        doc.getInteger("stock"),
                        date,
                        status);

                lisObjects.add(item);
            } catch (Exception e) {
                System.out.println("Error parsing expired_date for product: " + doc.getString("name"));
                e.printStackTrace();
            }
        }

        return lisObjects;
    }

    private static Status statusExpired(LocalDate curDate) {
        LocalDate dateConverted = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(dateConverted, curDate);

        if (daysBetween > 0 && daysBetween < 16) {
            return Status.near_expired;
        } else if (daysBetween >= 16) {
            return Status.valid;
        } else {
            return Status.expired;
        }
    }

    @Override
    public void insert(Object data) {
        try {
            Products item = (Products) data;

            this.collection.insertOne(item.toDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object data) {
        try {
            Products item = (Products) data;

            Document result = this.collection.find(Filters.eq("code", item.getId())).first();

            // Kiểm tra xem có tìm thấy document không
            if (result != null) {
                // Cập nhật document nếu tìm thấy
                this.collection.updateOne(
                        Filters.eq("code", item.getId()), // Điều kiện tìm kiếm
                        new Document("$set", item.toDocument()));// updateAll document
                System.out.println("Document đã được cập nhật.");
            } else {
                System.out.println("Không tìm thấy document.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object data) {
        try {
            Products item = (Products) data;

            Document result = this.collection.find(Filters.eq("code", item.getId())).first();

            // Kiểm tra xem có tìm thấy document không
            if (result != null) {
                // Cập nhật document nếu tìm thấy
                this.collection.deleteOne(result);
            } else {
                System.out.println("Không tìm thấy document.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
