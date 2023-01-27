package com.medicalretrieval.utils;

import com.medicalretrieval.pojo.Document;

import java.util.Date;
import java.util.Set;

public class ToDocumentClass {
    /**
     * <pre>将数据转换为Document类</pre>
     * @param title 文章的标题
     * @param author
     * @param date
     * @param PDFPath
     * @return
     */
    public static Document Transition(String title, Set<String> author, Date date, String PDFPath){
        Document document = new Document();
        document.setTitle(title);
        document.setAuthor(author);
        document.setDate(date);
        return document;

    }
}
