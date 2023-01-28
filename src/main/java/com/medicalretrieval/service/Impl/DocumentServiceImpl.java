package com.medicalretrieval.service.Impl;

import com.medicalretrieval.Repository.DocumentRepository;
import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.pojo.ReturnDoc;
import com.medicalretrieval.service.DocumentService;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Override
    public void saveAll(List<Document> documents) {
        documentRepository.saveAll(documents);
    }

    /**
     * @param document 文档类
     */
    @Override
    public void saveOne(Document document) {
        documentRepository.save(document);
    }

    /**
     * @param title 文章标题
     */
    @Override
    public List<ReturnDoc> findDocumentByTitle(String title) {
        List<Document> documents = documentRepository.findByTitle(title);
        List<ReturnDoc> returnDocs = new ArrayList<>();
        for (Document d :
            documents) {
            returnDocs.add(new ReturnDoc(d,1));
        }
        return returnDocs;
    }

    /**
     *
     * @param authors 搜索的作者名
     * @return 结果集
     */
    @Override
    public List<ReturnDoc> findDocumentByAuthors(Set<String> authors) {
        List<Document> documents = documentRepository.findByAuthor(authors);

        List<ReturnDoc> returnDocs = new ArrayList<>();
        for (Document d :
                documents) {
            returnDocs.add(new ReturnDoc(d,1));
        }
        return returnDocs;
    }


}
