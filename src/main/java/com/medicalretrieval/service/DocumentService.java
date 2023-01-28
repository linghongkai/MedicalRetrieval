package com.medicalretrieval.service;

import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.pojo.ReturnDoc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface DocumentService {
    void saveAll(List<Document> documents);
    void saveOne(Document document);


    List<ReturnDoc> findDocumentByTitle(String title);


    List<ReturnDoc> findDocumentByAuthors(Set<String> authors);
}
