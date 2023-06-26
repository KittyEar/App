package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class XlsxToCsvConverter {
    public static void main(String[] args) {
        String xlsxFilePath = getResourceFilePath() + "excel/test.xlsx"; // xlsx文件在resources目录下的相对路径
        System.out.println(xlsxFilePath);
        String csvFilePath = getResourceFilePath() + "csv" +"/test.csv"; // 生成的csv文件的路径
        System.out.println(csvFilePath);
        try {
            FileInputStream inputStream = new FileInputStream(xlsxFilePath);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            File csvFile = new File(csvFilePath);
            csvFile.createNewFile(); // 创建CSV文件

            FileWriter csvWriter = new FileWriter(csvFile);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    csvWriter.append(cellValue).append(",");
                }
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("XLSX to CSV conversion completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getResourceFilePath() {
        ClassLoader classLoader = XlsxToCsvConverter.class.getClassLoader();
        return classLoader.getResource("").getPath();
    }

    private static String getCellValueAsString(Cell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
                    break;
            }
        }
        return cellValue;
    }
}
class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("A","B","B","C"));
                                 // 浅拷贝一手
        for (String s : new ArrayList<>(list)) {
            if (s.equals("B")) {
                list.remove(s);
            }
        }
        System.out.println(list);
    }//就要边遍历边删除，
}
