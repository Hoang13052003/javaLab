package com.example.ManageProducts.models;

import java.time.LocalDate;

import com.example.ManageProducts.interfaces.Builder;

public class ProductsBuilder implements Builder {
    public int id;
    public String name;
    public double price;
    public int stock;
    public LocalDate expired_date;
    public Status status;

    // demo code builder thu cong
    @Override
    public ProductsBuilder addId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductsBuilder addName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ProductsBuilder addPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public ProductsBuilder addStock(int stock) {
        this.stock = stock;
        return this;
    }

    @Override
    public ProductsBuilder addExpired_date(LocalDate expired_date) {
        this.expired_date = expired_date;
        return this;
    }

    @Override
    public ProductsBuilder addStatus(Status status) {
        this.status = status;
        return this;
    }

    @Override
    public Products build() {
        return new Products(id, name, price, stock, expired_date, status);
    }
}
