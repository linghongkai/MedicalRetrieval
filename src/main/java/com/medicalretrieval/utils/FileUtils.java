package com.medicalretrieval.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

public class FileUtils {
    /**
     * 查看MultipartFile代码可知：可以通过getInputStream()方法获取MultipartFile字节流
     * 既然有了字节流，那一切皆有可能了
     * 用最常规的字节流处理方式，输出一个File文件就可以了
     * 需要注意的是这种方式会把文件输出到磁盘项目目录下，File使用完后需要手动删除deleteTempFile()
     * @param file
     * @return
     */
    public static File multipartFileToFIle(MultipartFile file){
        try {
            File toFile=null;
            if (file.equals("") || file.getSize() <= 0) {
                file = null;
            } else {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
                inputStreamToFile(ins, toFile);
                ins.close();
            }
            return toFile;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * @param ins   :输入流
     * @param file  :输出file对象
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = Files.newOutputStream(file.toPath());
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            //把字节流读到缓冲区buffer，从缓存区的坐标0开始放，放到8192
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            //关闭输入输出流
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     * @param file  :删除file对象
     */
    public static void deleteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }
}
