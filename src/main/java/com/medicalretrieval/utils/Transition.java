package com.medicalretrieval.utils;

import com.medicalretrieval.pojo.Document;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <pre>将传来的数据转化为规定的格式</pre>
 * @author 梁宏凯
 */
public class Transition {

    public static Document TransitionDocument(MultipartFile file) throws IOException {
        if(file.isEmpty()) return null;
        String fileName = file.getOriginalFilename();
        file.transferTo(new File(""));
        return null;
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
