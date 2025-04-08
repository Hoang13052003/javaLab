package com.example.ManageProducts.models;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import com.example.ManageProducts.interfaces.Prototype;

// import lombok.Builder;
// import lombok.Data;

import org.bson.Document;

//demo dung thu vien lombok su dung builder
// @Data
// @Builder
public class Products implements Prototype {
    // private static volatile Products instance;

    private int id;
    private String name;
    private double price;
    private int stock;
    private LocalDate expired_date;
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(LocalDate expired_date) {
        this.expired_date = expired_date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Products(int id, String name, double price, int stock, LocalDate expired_date, Status status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.expired_date = expired_date;
        this.status = status;
    }

    @Override
    public Prototype Clone() {
        return new Products(this.id, this.name, this.price, this.stock, this.expired_date, this.status);
    }

    public String toStringCSV() {
        return String.join(",",
                String.valueOf(id),
                name,
                String.valueOf(price),
                String.valueOf(stock),
                expired_date.format(DataExemple.DATE_FORMATTER), // Format lại ngày theo dd/MM/yyyy
                status.name() // Lấy tên của Enum (EXPIRED, NEAR_EXPIRED, VALID)
        );
    }

    // Convert Product thành Document để ghi vào MongoDB
    public Document toDocument() {
        Document document = new Document();
        document.append("code", id);
        document.append("name", name);
        document.append("price", price);
        document.append("stock", stock);
        document.append("expired_date", expired_date.format(DataExemple.DATE_FORMATTER));
        return document;
    }

    public void display() {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String stringPrice = vndFormat.format(price).replace("₫", " VND");
        System.out.println("Product{ " + "id: " + id + ", name: " + name + ", price: "
                + stringPrice
                + ", stock: " + stock
                + ", expired_date: " + expired_date.format(DataExemple.DATE_FORMATTER) + ", status: "
                + status + "}");
    }
}
