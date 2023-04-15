package com.medicalretrieval.utils;

import com.medicalretrieval.api.oss.OssDao;
import com.medicalretrieval.pojo.elasticsearch.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class Transition {

    @Autowired
    private OssDao ossDao;

    public @Nullable File TransitionDocument(@NotNull MultipartFile multipartFile,Document document) throws IOException {
        if(multipartFile.isEmpty()) return null;

        String fileName = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[0]+System.currentTimeMillis()+".pdf";//文件名+时间戳
        document.setTitle(multipartFile.getOriginalFilename().split("\\.pdf")[0]);//标题依旧是文件名，没有加上时间戳
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置URL，并上传文件
        ossDao.upload(multipartFile,fileName);
        document.setUrl(fileName);

        return file;
    }

    /**
     * 批量返回Document类
     *
     * @return 返回Document链表
     */
    public List<File> TransitionDocument(MultipartFile[] multipartFiles,List<Document> documents) throws IOException {
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
