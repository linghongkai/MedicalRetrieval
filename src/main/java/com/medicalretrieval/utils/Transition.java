package com.medicalretrieval.utils;

import com.medicalretrieval.api.oss.OssDao;
import com.medicalretrieval.pojo.elasticsearch.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>将传来的数据转化为规定的格式</pre>
 * @author 梁宏凯
 */
public class Transition {

    public static @Nullable File TransitionDocument(@NotNull MultipartFile multipartFile,Document document) throws IOException {
        if(multipartFile.isEmpty()) return null;

        String fileName = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[0]+System.currentTimeMillis()+".pdf";//文件名+时间戳
        document.setTitle(multipartFile.getOriginalFilename());//标题依旧是文件名，没有加上时间戳

//        multipartFile.transferTo(new File("doc/"+fileName));//将MultipartFile保存一份file
//        File file = new File("doc/"+fileName);//打开file
//        PDFUtils.ReadPDF(file,document);//解析file，把内容存到document中去

        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置URL，并上传文件
        document.setUrl(OssDao.upload(multipartFile,fileName));

//        boolean delete = file.delete();//删除file

        return file;
    }

    /**
     * 批量返回Document类
     *
     * @return 返回Document链表
     */
    public static List<File> TransitionDocument(MultipartFile[] multipartFiles,List<Document> documents) throws IOException {
        List<File> files = new ArrayList<>();
        for (MultipartFile multipartFile :
                multipartFiles) {
            if(multipartFile.isEmpty())continue;
            File tem = null;
            Document document = new Document();
            files.add(TransitionDocument(multipartFile,document));
            documents.add(document);
        }
        return files;
    }

}
