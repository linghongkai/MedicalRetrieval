package com.medicalretrieval.controller;

import com.medicalretrieval.MedicalRetrievalApplication;
import com.medicalretrieval.pojo.elasticsearch.Document;
import com.medicalretrieval.pojo.elasticsearch.PageContent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MedicalRetrievalApplication.class)
class DocumentControllerTest {
    @Autowired
    DocumentController documentController ;
    @Test
    void saveBatch() {
    }

    @Test
    void saveOne() {
        Document document = new Document();
        document.setId(1004L);
        document.setTitle("人的一生");
        Set<String> strings = new HashSet<>();
        strings.add("梁宏凯");
        strings.add("庞少航");
        strings.add("王洪兵");
//        strings.add("李卓帅");
        document.setAuthor(strings);
        document.setDate(new Date());
        document.setPageSize(3);
        Set<PageContent> pageContents = new HashSet<>();
        pageContents.add(new PageContent(1L,"第一页",null));
        pageContents.add(new PageContent(2L,"第二页",null));
        pageContents.add(new PageContent(3L,"第三页",null));
        document.setPageContents(pageContents);
//        System.out.println(documentController.saveOne(document));
    }



}