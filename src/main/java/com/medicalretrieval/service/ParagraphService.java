package com.medicalretrieval.service;

import com.medicalretrieval.pojo.Paragraph;

import java.util.List;

public interface ParagraphService {
    void saveAll(List<Paragraph> paragraphs);
    List<Paragraph> findByContent(String content);
}
