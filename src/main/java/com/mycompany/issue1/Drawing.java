package com.mycompany.issue1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Drawing {

    List<Sheet> sheets;
    int totalWidth;
    int totalHeight;
    Node root;

    public void setSheets(List<Sheet> sheets) {
        this.sheets = sheets;
    }

    public Drawing(int totalWidth, int totalHeight) {
        this.sheets = new ArrayList<>();
        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.root = null;
    }

    public Drawing() {
        this.sheets = new ArrayList<>();
    }

    public void addSheet(Sheet sheet) {
        sheets.add(sheet);
        updateDimensions();
        System.out.println(importDrawing(this)); // إعادة رسم الشبكة وعرضها
    }

    private void updateDimensions() {
        int maxWidth = 0;
        int maxHeight = 0;
        for (Sheet sheet : sheets) {
            int sheetRight = sheet.startX + sheet.width;
            int sheetBottom = sheet.startY + sheet.height;
            if (sheetRight > maxWidth) {
                maxWidth = sheetRight;
            }
            if (sheetBottom > maxHeight) {
                maxHeight = sheetBottom;
            }
        }
        totalWidth = maxWidth;
        totalHeight = maxHeight;
    }

    private void writeNameInsideSheet(char[][] grid, Sheet sheet) {
        int textStartX = sheet.startX + 1; // بداية النص تجنباً للخط العمودي
        int textStartY = sheet.startY + 1; // بداية النص تجنباً للخط الأفقي
        String name = sheet.name;

        // حساب منتصف الورقة لبدء النص
        int midPoint = (int) ((sheet.width - name.length()) / 2);
        if (midPoint < 0) {
            midPoint = 0; // في حال كان اسم الورقة أطول من عرضها
        }
        // كتابة النص داخل الورقة
        for (int i = 0; i < name.length() && textStartX + midPoint + i < sheet.startX + sheet.width - 1; i++) {
            grid[textStartY][textStartX + midPoint + i] = name.charAt(i);
        }
    }

    public void importDrawing(Drawing drawing, File filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        // إنشاء شبكة لرسم الورقة
        char[][] grid = new char[drawing.totalHeight][drawing.totalWidth];
        for (int i = 0; i < drawing.totalHeight; i++) {
            for (int j = 0; j < drawing.totalWidth; j++) {
                grid[i][j] = ' ';
            }
        }

        // رسم كل ورقة صغيرة
        for (Sheet sheet : drawing.sheets) {
            // رسم الخطوط العمودية
            for (int y = sheet.startY; y < sheet.startY + sheet.height; y++) {
                grid[y][sheet.startX] = '|';
                grid[y][(int) (sheet.startX + sheet.width - 1)] = '|';
            }
            // رسم الخطوط الأفقية
            for (int x = sheet.startX; x < sheet.startX + sheet.width; x++) {
                grid[sheet.startY][x] = '-';
                grid[(int) (sheet.startY + sheet.height - 1)][x] = '-';
            }
            writeNameInsideSheet(grid, sheet);
        }

        // كتابة الشبكة إلى الملف
        for (char[] row : grid) {
            writer.write(row);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    public String importDrawing(Drawing drawing) {
        StringBuilder result = new StringBuilder();
        // إنشاء شبكة لرسم الورقة
        char[][] grid = new char[drawing.totalHeight][drawing.totalWidth];
        for (int i = 0; i < drawing.totalHeight; i++) {
            for (int j = 0; j < drawing.totalWidth; j++) {
                grid[i][j] = ' ';
            }
        }

        // رسم كل ورقة صغيرة
        for (Sheet sheet : drawing.sheets) {
            // رسم الخطوط العمودية
            for (int y = sheet.startY; y < sheet.startY + sheet.height; y++) {
                if (y >= 0 && y < drawing.totalHeight) {
                    if (sheet.startX >= 0 && sheet.startX < drawing.totalWidth) {
                        grid[y][sheet.startX] = '|';
                    }
                    if ((int) (sheet.startX + sheet.width - 1) >= 0
                            && (int) (sheet.startX + sheet.width - 1) < drawing.totalWidth) {
                        grid[y][(int) (sheet.startX + sheet.width - 1)] = '|';
                    }
                }
            }
            // رسم الخطوط الأفقية
            for (int x = sheet.startX; x < sheet.startX + sheet.width; x++) {
                if (x >= 0 && x < drawing.totalWidth) {
                    if (sheet.startY >= 0 && sheet.startY < drawing.totalHeight) {
                        grid[sheet.startY][x] = '-';
                    }
                    if ((int) (sheet.startY + sheet.height - 1) >= 0
                            && (int) (sheet.startY + sheet.height - 1) < drawing.totalHeight) {
                        grid[(int) (sheet.startY + sheet.height - 1)][x] = '-';
                    }
                }
            }
            writeNameInsideSheet(grid, sheet);
        }

        // تحويل الشبكة إلى نص
        for (char[] row : grid) {
            result.append(row);
            result.append('\n');
        }
        return result.toString();
    }
}
