package com.medicalretrieval.Repository;

import com.medicalretrieval.pojo.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends ElasticsearchRepository<Document,Long> {
}
