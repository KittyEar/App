package util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class WarCopier {

    public static void main(String[] args) {
        // 指定多个 target 目录的路径
        String[] targetDirs = {
                //"/path/to/project1/target/",
                //"/path/to/project2/target/",
                "D:\\IdeaProject\\CSVToJava\\target\\classes"
                // 添加更多项目的 target 目录路径
                // ...
        };

        // 指定复制到的目标目录
        String destinationDir = "C:\\Users\\kangy\\Desktop\\新建文件夹 (2)";

        for (String targetDir : targetDirs) {
            copyWars(targetDir, destinationDir);
        }
    }

    private static void copyWars(String sourceDir, String destinationDir) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(sourceDir), "*.war")) {
            for (Path file : directoryStream) {
                Path destinationFile = Paths.get(destinationDir).resolve(file.getFileName());
                Files.copy(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Copied " + file + " to " + destinationFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
