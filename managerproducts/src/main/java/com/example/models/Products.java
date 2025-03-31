package com.example.models;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Products {
    private int id;
    private String name;
    private double price;
    private int stock;
    private LocalDate expired_date;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Products(int id, String name, double price, int stock, LocalDate expired_date, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.expired_date = expired_date;
        this.status = status;
    }

    public void display() {
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String stringPrice = vndFormat.format(price).replace("₫", " VND");
        System.out.println("Product{ " + "id: " + id + ", name: " + name + ", price: "
                + stringPrice
                + ", stock: " + stock
                + ", expired_date: " + expired_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ", status: "
                + status + "}");
    }
}
