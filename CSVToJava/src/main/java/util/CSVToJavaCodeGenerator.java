package util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
public class CSVToJavaCodeGenerator {
    public static void main(String[] args) {
        String csvFile = CSVToJavaCodeGenerator.class.getClassLoader().getResource("").getPath() + "csv/test.csv";

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(csvFile)).build()) {
            String[] header = reader.readNext(); // 读取首行字段名称
            String[] types = reader.readNext();  // 读取第二行字段类型

            if (header != null && types != null) {
                generateJavaClass(header, types);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateJavaClass(String[] fields, String[] types) {
        StringBuilder codeBuilder = new StringBuilder();
        codeBuilder.append("public class YourEntityClass {\n");

        // 生成字段
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            String type = types[i];
            if (field.equals("")) {
                break;
            }
            String fieldType;
            if (type.equals("array")) {
                fieldType = "List";
            } else if (type.equals("array2")) {
                fieldType = "List<List<Integer>>";
            } else {
                fieldType = getJavaType(type);
            }

            codeBuilder.append("\tprivate ").append(fieldType).append(" ").append(field).append(";\n");
        }

        codeBuilder.append("\n");

        // 生成Getter和Setter方法
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (field.equals("")) {
                break;
            }
            String capitalizedField = capitalizeFirstLetter(field);
            String fieldType = types[i];

            String getterReturnType;
            if (fieldType.equals("array")) {
                getterReturnType = "List";
            } else if (fieldType.equals("array2")) {
                getterReturnType = "List<List<Integer>>";
            } else {
                getterReturnType = getJavaType(fieldType);
            }

            codeBuilder.append("\tpublic ").append(getterReturnType).append(" get").append(capitalizedField).append("() {\n");
            codeBuilder.append("\t\treturn ").append(field).append(";\n");
            codeBuilder.append("\t}\n\n");

            codeBuilder.append("\tpublic void set").append(capitalizedField).append("(").append(getterReturnType).append(" ").append(field).append(") {\n");
            codeBuilder.append("\t\tthis.").append(field).append(" = ").append(field).append(";\n");
            codeBuilder.append("\t}\n\n");
        }

        codeBuilder.append("}");

        String javaCode = codeBuilder.toString();
        System.out.println(javaCode);  // 输出生成的Java代码
        // 将javaCode写入到文件中
    }

    private static String getJavaType(String fieldType) {
        String javaType;
        switch (fieldType) {
            case "string":
                javaType = "String";
                break;
            case "int":
                javaType = "int";
                break;
            case "double":
                javaType = "double";
                break;
            // 添加其他数据类型的映射关系
            default:
                javaType = "String"; // 默认为String类型
                break;
        }
        return javaType;
    }

    private static String capitalizeFirstLetter(String field) {
        if (field.equals("")) {
            return "";
        }

        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }
}

