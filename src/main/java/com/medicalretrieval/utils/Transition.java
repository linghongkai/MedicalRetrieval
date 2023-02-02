package com.medicalretrieval.utils;

import com.medicalretrieval.api.oss.OssDao;
import com.medicalretrieval.pojo.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>将传来的数据转化为规定的格式</pre>
 * @author 梁宏凯
 */
public class Transition {

    public static @Nullable Document TransitionDocument(@NotNull MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) return null;
        Document document = new Document();//创建一个document对象

        String fileName = multipartFile.getOriginalFilename()+System.currentTimeMillis();//文件名+时间戳
        document.setTitle(multipartFile.getOriginalFilename());//标题依旧是文件名，没有加上时间戳

        multipartFile.transferTo(new File("./doc/"+fileName));//将MultipartFile保存一份file
        File file = new File("./doc/"+fileName);//打开file
        PDFUtils.ReadPDF(file,document);//解析file，把内容存到document中去


        //设置URL，并上传文件
        document.setUrl(OssDao.upload(multipartFile,fileName));

        boolean delete = file.delete();//删除file
        return document;
    }

    /**
     * 批量返回Document类
     * @param files 文件流
     * @return 返回Document链表
     */
    public static List<Document> TransitionDocument(MultipartFile[] files) throws IOException {
        List<Document> documents = new ArrayList<>();
        for (MultipartFile file :
                files) {
            if(file.isEmpty())continue;
            documents.add(TransitionDocument(file));
        }
        return documents;
    }

}
