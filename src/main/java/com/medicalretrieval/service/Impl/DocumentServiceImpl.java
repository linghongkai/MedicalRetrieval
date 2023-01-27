package com.medicalretrieval.service.Impl;

import com.medicalretrieval.Repository.DocumentRepository;
import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.service.DocumentService;
import org.elasticsearch.common.inject.Inject;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;

public class DocumentServiceImpl implements DocumentService {

    @Inject
    DocumentRepository documentRepository;

    @Inject
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Override
    public void saveAll(List<Document> documents) {
        documentRepository.saveAll(documents);
    }
}
