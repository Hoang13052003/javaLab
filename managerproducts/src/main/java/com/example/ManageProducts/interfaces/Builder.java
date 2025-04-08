package com.example.ManageProducts.interfaces;

import java.time.LocalDate;

import com.example.ManageProducts.models.Products;
import com.example.ManageProducts.models.Status;

public interface Builder {
    Builder addId(int id);

    Builder addName(String name);

    Builder addPrice(double price);

    Builder addStock(int stock);

    Builder addExpired_date(LocalDate expired_date);

    Builder addStatus(Status status);

    Products build();
}
