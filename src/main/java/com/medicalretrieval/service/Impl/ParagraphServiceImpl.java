package com.medicalretrieval.service.Impl;

import com.medicalretrieval.Repository.ParagraphRepository;
import com.medicalretrieval.pojo.Paragraph;
import com.medicalretrieval.service.ParagraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ParagraphServiceImpl implements ParagraphService {
    @Autowired
    ParagraphRepository paragraphRepository;
    /**
     * <pre>
     *     由于段落极大部分都是一个链表。所以不再写添加一份段落的代码。内部调用，不对用户开放
     * </pre>
     * @param paragraphs 段落的链表
     */
    @Override
    public void saveAll(List<Paragraph> paragraphs) {
        paragraphRepository.saveAll(paragraphs);
    }

    /**
     * <pre>通过某一段落的内容进行文档的查找</pre>
     * @param content 某一段落的内容
     * @return 段落的链表
     */
    @Override
    public List<Paragraph> findByContent(String content) {
        return paragraphRepository.findByContentOrImgInfos(content);
    }
}
