package com.example.ManageProducts.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.ManageProducts.models.DataExemple;
import com.example.ManageProducts.models.Products;
import com.example.ManageProducts.models.ProductsBuilder;
import com.example.ManageProducts.models.Status;

public class FileHandler {
    public static void writeToExcel(String path, List<Products> products) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "id", "name", "price", "stock", "sum_price", "expired_date", "status" };
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            // Write product data
            int rowIndex = 1;
            for (Products p : products) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getName());
                row.createCell(2).setCellValue(p.getPrice());
                row.createCell(3).setCellValue(p.getStock());
                row.createCell(4).setCellValue(p.getPrice() * p.getStock());
                row.createCell(5).setCellValue(p.getExpired_date().toString());
                row.createCell(6).setCellValue(p.getStatus().name());
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(path)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToCSV(String path, List<Products> products) throws IOException {
        String header = "id,name,price,stock,sum_price,expired_date,status";
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            writer.write(header);
            writer.newLine();

            products.stream()
                    .map(Products::toStringCSV)
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException("Error writing to file", e);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<Products> readFromFile(String path) throws IOException {
        List<Products> products = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(path)); Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> columnIndexMap = new HashMap<>();

            Row headerRow = sheet.getRow(0);
            for (Cell cell : headerRow) {
                columnIndexMap.put(cell.getStringCellValue().toLowerCase(), cell.getColumnIndex());
            }

            if (!columnIndexMap.containsKey("id") || !columnIndexMap.containsKey("name") ||
                    !columnIndexMap.containsKey("price") || !columnIndexMap.containsKey("stock") ||
                    !columnIndexMap.containsKey("expired_date")) {
                System.out.println("Missing some important columns in the Excel file.");
                return products;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Skip header row
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                int id = (int) row.getCell(columnIndexMap.get("id")).getNumericCellValue();
                String name = row.getCell(columnIndexMap.get("name")).getStringCellValue();
                double price = row.getCell(columnIndexMap.get("price")).getNumericCellValue();
                int stock = (int) row.getCell(columnIndexMap.get("stock")).getNumericCellValue();

                // Handle expiration date
                Cell dateCell = row.getCell(columnIndexMap.get("expired_date"));
                LocalDate expiredDate = null;
                if (dateCell.getCellType() == CellType.NUMERIC) {
                    expiredDate = dateCell.getLocalDateTimeCellValue().toLocalDate();
                } else {
                    expiredDate = LocalDate.parse(dateCell.getStringCellValue(), formatter);
                }

                products.add(new Products(id, name, price, stock, expiredDate, statusExpired(expiredDate)));
            }
        } catch (Exception e) {
            System.out.println("Error reading file " + path);
        }
        return products;
    }

    public static List<Products> readFromFileCSV(String path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {

            return br.lines()
                    .skip(1)
                    .map(line -> {
                        try {
                            return fromProductLine(line, ",");
                        } catch (Exception e) {
                            System.err.println("Skipping invalid line: " + line + " - Error: " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        }
    }

    public static List<Products> readFromFileTxt(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.lines()
                    .map(line -> {
                        try {
                            return fromProductLine(line, ",");
                        } catch (Exception e) {
                            System.err.println("Skipping invalid line: " + line + " - Error: " + e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
    }

    private static Products fromProductLine(String line, String delimiter) throws Exception {
        String[] data = line.split(delimiter);

        if (data.length != 5) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }

        try {
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            double price = Double.parseDouble(data[2]);
            int stock = Integer.parseInt(data[3]);
            LocalDate expired_date = LocalDate.parse(data[4].trim(), DataExemple.DATE_FORMATTER);
            Status status = statusExpired(expired_date);
            // Su dung thu vien Lombok
            // return
            // Products.builder().id(id).name(name).price(price).stock(stock).expired_date(expired_date)
            // .status(status).build();

            // Su dung code builder
            return new ProductsBuilder().addId(id).addName(name).addPrice(price).addStock(stock)
                    .addExpired_date(expired_date).addStatus(status).build();
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new Exception("Error parsing line: " + line + " - " + e.getMessage().toString(), e);
        }
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
}
