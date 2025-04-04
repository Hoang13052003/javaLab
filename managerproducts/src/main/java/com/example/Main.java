package com.example;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFileChooser;

import com.example.services.ExportHandler;
import com.example.services.ManagerProducts;

public class Main {

    public static void main(String[] args) {
        ManagerProducts manager = new ManagerProducts();
        Scanner input = new Scanner(System.in);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // doc file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chon file can mo....");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int choice;
        do {
            System.out.println("Danh sach chuc nang:");
            System.out.println("1. Doc file Excel ...");
            System.out.println("2. Xu ly data");
            System.out.println("3. Xuat file ..");
            System.out.println("4. Xuat file PDF");
            System.out.println("5. Insert All data vao database");
            System.out.println("6. Thoat!");
            System.out.print("Chon: ");
            choice = Integer.parseInt(System.console().readLine());

            switch (choice) {
                case 1: {
                    fileChooser
                            .setCurrentDirectory(new File("D:\\DATA\\2025\\Lab\\lab1\\managerproducts\\data\\import"));

                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        Callable<Boolean> task = () -> {
                            System.out.println("Dang doc file.....");
                            boolean result = manager.readFromFile(path);
                            if (result) {
                                System.out.println("Doc file thanh cong!");
                                manager.displayProducts();
                            } else {
                                System.out.println("Doc file that bai!");
                            }
                            return result;
                        };
                        try {
                            boolean success = executor.submit(task).get();
                            if (!success) {
                                System.out.println("Co loi khi doc file!");
                            }
                        } catch (Exception e) {
                            System.out.println("Loi: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Khong chon file nao!");
                    }
                }
                    break;
                case 2: {
                    Callable<Void> task = () -> {
                        System.out.println("Dang xu ly du lieu...");
                        manager.updatePriceAndStock();
                        System.out.println("Xu ly du lieu hoan tat!");
                        manager.displayProducts();
                        return null;
                    };
                    try {
                        executor.submit(task).get();
                    } catch (Exception e) {
                        System.out.println("Loi: " + e.getMessage());
                    }
                }
                    break;
                case 3: {
                    Callable<Boolean> task = () -> {
                        System.out.println("Dang xuat file...");
                        String path = "D:\\DATA\\2025\\Lab\\lab1\\managerproducts\\data\\export\\export.csv";
                        boolean result = manager.writeToFile(path);
                        if (result) {
                            System.out.println("Ghi file thanh cong!");
                        } else {
                            System.out.println("Ghi that bai!");
                        }
                        return result;
                    };
                    try {
                        boolean success = executor.submit(task).get();
                        if (!success) {
                            System.out.println("Co loi khi xuat file!");
                        }
                    } catch (Exception e) {
                        System.out.println("Loi: " + e.getMessage());
                    }
                }
                    break;
                case 4: {
                    try {
                        ExportHandler.exportToPDF("temp1.html", manager.getListProduct());
                        System.out.println("Xuat file PDF thanh cong!");
                    } catch (Exception e) {
                        System.out.println("Xuat file PDF khong thanh cong: " + e.getMessage());
                    }
                }
                    break;

                case 5: {
                    manager.insertAll();
                    System.out.print("Insert data vao database thanh cong!");
                }
                    break;

                case 6: {
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    input.close();
                    executor.shutdown();
                    System.exit(0);
                }
                    break;

                default:
                    System.out.println("Lua chon khong hop le!");
                    break;
            }

        } while (true);

    }
}