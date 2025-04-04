package com.example.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.database.handler.ProductsHandler;
import com.example.models.DataExemple;
import com.example.models.Products;
import com.example.models.Status;

public class ManagerProducts {
    private List<Products> listProduct = new ArrayList<>();
    private ProductsHandler db = new ProductsHandler();

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
            FileHandler.writeToCSV(path, listProduct);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updatePriceAndStock() {
        for (Products item : listProduct) {
            if (Status.near_expired.equals(item.getStatus())) {
                double discount = item.getStock() > 5 ? 0.1 : 0.05;
                item.setPrice(item.getPrice() * (1 - discount));
            }
            if (Status.expired.equals(item.getStatus())) {
                item.setStock(0);
            }
        }

        // cach khac
        // listProduct.stream()
        // .filter(item -> Status.near_expired.equals(item.getStatus()))
        // .forEach(item -> {
        // double discount = item.getStock() > 5 ? 0.1 : 0.05;
        // item.setPrice(item.getPrice() * (1 - discount));
        // });

        // listProduct.stream()
        // .filter(item -> Status.expired.equals(item.getStatus()))
        // .forEach(item -> item.setStock(0));
    }

    public Products findProductById(int id) {
        return listProduct.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Khong tim thay san pham"));
    }

    public void insertOne(Integer id) {
        db.insert(findProductById(id));
    }

    public void insertAll() {
        for (Products products : listProduct) {
            db.insert(products);
        }
    }

    private Products inputDataProducts(Scanner input) {
        while (true) {
            System.out.println("Ban co muon nhap thong tin san pham khong? (Y/N)");
            String choice = input.nextLine().trim();
            if (choice.equalsIgnoreCase("N")) {
                break;
            } else if (!choice.equalsIgnoreCase("Y")) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng nhập lại.");
                continue;
            }
            System.out.println("Nhap thong tin san pham:");
            System.out.print("Nhap ma san pham:");
            String id = input.nextLine();
            System.out.print("Nhap ten san pham:");
            String name = input.nextLine();
            System.out.print("Nhap gia ban:");
            double price = Double.parseDouble(input.nextLine());
            System.out.print("Nhap so luong:");
            int stock = Integer.parseInt(input.nextLine());
            System.out.print("Nhap ngay het han (dd/MM/yyyy):");
            String expired_date = input.nextLine();
            // System.out.print("Nhap trang thai san pham (EXPIRED, NEAR_EXPIRED, VALID):");
            // String status = input.nextLine();
            return new Products(Integer.parseInt(id), name, price, stock,
                    LocalDate.parse(expired_date, DataExemple.DATE_FORMATTER),
                    null);

        }
        return null;
    }

    public void removeProductById(int id) throws Exception {
        try {
            listProduct.removeIf(item -> item.getId() == id);
        } catch (Exception e) {
            throw new Exception("Error removing product: " + e.getMessage(), e);
        }
    }

    public void updateStock(int id, int stock) {
        findProductById(id).setStock(stock);

        // cach khac
        // listProduct.stream()
        // .filter(item -> item.getId() == id)
        // .findFirst()
        // .ifPresent(item -> item.setStock(stock));
    }

    public void addNewProduct(Products product) {
        Scanner input = new Scanner(System.in);
        listProduct.add(inputDataProducts(input));
        input.close();
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
