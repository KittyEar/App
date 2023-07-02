package util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 多个文件拼接成一个文件
 */
public class FileConcatenate {

    public static void main(String[] args) {
        String folderPath = getSrcResourcePath() + "\\mText";
        String outputPath = getSrcResourcePath() + "\\textResult\\result.text";
        System.out.println(outputPath);

        File outputDirectory = new File(outputPath).getParentFile();
        if (!outputDirectory.exists()) {//如果目录不存在就创建
            if (outputDirectory.mkdirs()) {
                System.out.println(outputPath + "make succeed");
            }
        }

        try (RandomAccessFile outputFile = new RandomAccessFile(outputPath, "rw");
             FileChannel outputChannel = outputFile.getChannel()) {

            File[] files = new File(folderPath).listFiles();
            if (files == null) {
                System.out.println("文件夹为空");
                return;
            }

            for (File file : files) {
                try (RandomAccessFile inputFile = new RandomAccessFile(file, "r");
                     FileChannel inputChannel = inputFile.getChannel()) {

                    long fileSize = inputChannel.size();
                    MappedByteBuffer buffer = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
                    // 添加换行符
                    if (outputChannel.size() > 0) {
                        ByteBuffer newline = ByteBuffer.wrap(System.lineSeparator().getBytes());
                        //System.out.println("写入" + outputChannel.write(newline) + "字节");
                        outputChannel.write(newline);
                    }
                    System.out.println("写入" + outputChannel.write(buffer) + "字节");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从target目录倒推src目录
     * @return 返回src 中的 resource路径
     */
    private static String getSrcResourcePath() {
        // 获取当前类的 ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        // 获取资源文件的路径
        String resourcePath = new File(classLoader.getResource("").getPath()).getParent();

        // 推导 src/main/resources 目录的路径
        String srcResourcesPath = new File(resourcePath).getParent() + "\\src\\resource";
        return srcResourcesPath;
    }
}


