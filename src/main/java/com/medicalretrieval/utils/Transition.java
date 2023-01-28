package com.medicalretrieval.utils;

import com.medicalretrieval.pojo.Document;

import java.util.Date;
import java.util.Set;

/**
 * <pre>将传来的数据转化为规定的格式</pre>
 * @author 梁宏凯
 */
public class Transition {
    /**
     * <pre>将数据转换为Document类</pre>
     * @param title 文章的标题
     * @param author 文章的作者
     * @param date 文章的日期
     * @param PDFPath 文章的路径
     * @return 将文档类返回
     */
    public static Document TransitionDocument(String title, Set<String> author, Date date, String PDFPath){
        Document document = new Document();
        document.setTitle(title);
        document.setAuthor(author);
        document.setDate(date);
        return document;

    }

}
