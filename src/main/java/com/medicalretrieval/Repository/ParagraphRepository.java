package com.medicalretrieval.Repository;

import com.medicalretrieval.pojo.elasticsearch.Paragraph;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParagraphRepository extends ElasticsearchRepository<Paragraph,Long> {
    List<Paragraph> findByContentOrImgInfos(String content);
}
