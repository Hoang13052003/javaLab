package com.example.ManageProducts.services;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.ManageProducts.models.Products;
import com.itextpdf.html2pdf.HtmlConverter;

public class ExportHandler {
    /**
     * Processes an HTML template by injecting data using Thymeleaf.
     *
     * @param template the name of the template file (without extension) to be
     *                 processed
     * @param data     a map of key-value pairs to be injected into the template
     * @return the processed template as a String with data injected
     * @throws Exception if an error occurs during the template processing
     */

    public static String processTemplate(String template, Map<String, Object> data) throws Exception {
        // Cau hinh Thymeleaf
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        // Thay the data vao template
        Context con = new Context();
        data.forEach(con::setVariable);

        return engine.process(template, con);
    }

    public static void exportToPDF(String path, List<Products> products)
            throws Exception {
        // create data
        Map<String, Object> data = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        List<Map<String, Object>> items = createData(products);

        data.put("date", formattedDate);
        data.put("items", items);

        // Load template HTML và thay thế dữ liệu
        String processedHtml = processTemplate(path, data);

        // Xác định đường dẫn thư mục xuất file
        String exportDirPath = "D:\\DATA\\2025\\Lab\\lab1\\managerproducts\\data\\export";
        File exportDir = new File(exportDirPath);
        if (!exportDir.exists()) {
            boolean created = exportDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            if (!created) {
                throw new RuntimeException("Not create folder: " + exportDir.getAbsolutePath());
            }
        }

        // Xác định đường dẫn file PDF
        String outputPath = exportDirPath + File.separator + "reportDemo.pdf";

        // Xuất file PDF
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            HtmlConverter.convertToPdf(processedHtml, fos);
            System.out.println("Xuat PDF thanh cong: " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi khi xuat file PDF: " + e.getMessage());
        }

    }

    private static List<Map<String, Object>> createData(List<Products> list) {
        List<Map<String, Object>> itemLists = new ArrayList<>();
        for (Products item : list) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", item.getId());
            temp.put("name", item.getName());
            temp.put("price", item.getPrice());
            temp.put("expired_date", item.getExpired_date());
            temp.put("status", item.getStatus());
            itemLists.add(temp);
        }
        return itemLists;
    }

}
