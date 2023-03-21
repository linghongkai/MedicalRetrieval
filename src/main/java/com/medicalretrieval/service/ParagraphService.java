package com.medicalretrieval.service;

import com.medicalretrieval.pojo.elasticsearch.Paragraph;

import java.util.List;

public interface ParagraphService {
    void saveAll(List<Paragraph> paragraphs);
    List<Paragraph> findByContent(String content);
}
