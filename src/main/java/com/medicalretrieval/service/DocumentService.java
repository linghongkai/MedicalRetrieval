package com.medicalretrieval.service;

import com.medicalretrieval.pojo.Document;

import java.util.List;

public interface DocumentService {
    void saveAll(List<Document> documents);

}
