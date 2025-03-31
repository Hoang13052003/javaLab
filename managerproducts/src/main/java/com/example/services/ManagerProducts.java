package com.example.services;

import java.util.ArrayList;
import java.util.List;
// import java.util.function.Consumer;

import com.example.models.Products;

public class ManagerProducts {
    private List<Products> listProduct = new ArrayList<>();

    public List<Products> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Products> listProduct) {
        this.listProduct = listProduct;
    }

    public boolean readFromFile(String path) {
        try {
            this.listProduct = FileHandler.readFromFile(path);
            return true;
        } catch (Exception e) {
            System.out.println("Loi: " + e);
            return false;
        }
    }

    public boolean writeToFile(String path) {
        try {
            FileHandler.writeToExcel(path, listProduct);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePriceAndStock() {
        for (Products item : listProduct) {
            if ("near_expired".equalsIgnoreCase(item.getStatus())) {
                double discount = item.getStock() > 5 ? 0.1 : 0.05;
                item.setPrice(item.getPrice() * (1 - discount));
            }
            if ("expired".equalsIgnoreCase(item.getStatus())) {
                item.setStock(0);
            }
        }
    }

    public void displayProducts() {
        System.out.println("Danh sach san pham: ");
        listProduct.forEach(Products::display);

        // test lamda java 8
        // Consumer<Products> method = (product) -> {
        // product.display();
        // };
        // listProduct.forEach(method);
    }

}
