package com.medicalretrieval.service.Impl;

import com.medicalretrieval.Repository.DocumentRepository;
import com.medicalretrieval.pojo.elasticsearch.Document;
import com.medicalretrieval.pojo.elasticsearch.ReturnDoc;
import com.medicalretrieval.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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
            returnDocs.add(new ReturnDoc(d,1, (String) null, 0.0F));
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
//            returnDocs.add(new ReturnDoc(d,1,null));
        }
        return returnDocs;
    }

    /**
     * @param id 文档的id
     * @return 返回ReturnDoc的链表
     */
    @Override
    public Document findById(List<Long> id) {
        Iterable<Document> documents = documentRepository.findAllById(id);
        Document document = new Document();
        for (Document d :
                documents) {
            document = d;
        }
        return document;
    }


}
