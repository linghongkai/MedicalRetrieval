package com.medicalretrieval.service;

import com.medicalretrieval.pojo.elasticsearch.Document;
import com.medicalretrieval.pojo.elasticsearch.ReturnDoc;

import java.util.List;
import java.util.Set;


public interface DocumentService {
    void saveAll(List<Document> documents);
    void saveOne(Document document);


    List<ReturnDoc> findDocumentByTitle(String title);


    List<ReturnDoc> findDocumentByAuthors(Set<String> authors);

    Document findById(List<Long> id);
}
