package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.pojo.PageContent;
import com.medicalretrieval.pojo.Paragraph;
import com.medicalretrieval.pojo.ReturnDoc;
import com.medicalretrieval.service.DocumentService;

import com.medicalretrieval.service.ParagraphService;
import com.medicalretrieval.utils.Transition;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     对于文档的控制
 * </pre>
 * @author 梁宏凯
 */
@RequestMapping("/item/")
@RestController
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @Autowired
    ParagraphService paragraphService;

    /**
     * 把文档的段落单独分出来
     * @param document 一份文档
     * @param paragraphs 文档中的段落需要传入到该链表中
     */
    private void extract(@NotNull Document document, List<Paragraph> paragraphs){
        Long id = document.getId();
        Set<PageContent> pageContents = document.getPageContents();
        for (PageContent p :
                pageContents) {
            paragraphs.add(new Paragraph((id * 10000) + p.getId(), p.getContent(), p.getImgInfos()));
        }
    }


    /**
     * <pre>批量保存pdf</pre>
     * @return 提交结果
     */
    @PostMapping("saveBatch")
    public boolean saveBatch(Integer id, MultipartFile[] files) throws IOException {

        List<Document> documents = Transition.TransitionDocument(files);
        if(CollectionUtils.isEmpty(documents)){
            return false;
        }
        documentService.saveAll(documents);
        List<Paragraph> paragraphs = new ArrayList<>();
        for (Document d :
                documents) {
            extract(d, paragraphs);
        }
        paragraphService.saveAll(paragraphs);
        return true;
    }

    /**
     * <pre>保存一个数据</pre>
     * @param id 文档类
     * @return 提交结果
     */
    @PostMapping("saveOne")
    public boolean saveOne(Integer id, MultipartFile file) throws IOException {
        Document document = Transition.TransitionDocument(file);
        if(document==null){
            return false;
        }
        documentService.saveOne(document);
        List<Paragraph> paragraphs = new ArrayList<>();
        extract(document,paragraphs);
        paragraphService.saveAll(paragraphs);
        return true;
    }


    /**
     * <pre>通过文章标题查找文档</pre>
     * @param title 文章标题
     * @return 文档类的链表
     */
    @GetMapping("findByTitle")
    public List<ReturnDoc> findDocumentByTitle(String title){
        return documentService.findDocumentByTitle(title);
    }


    /**
     * <pre>通过作者查找文档</pre>
     * @param authors 作者集
     * @return 文档结果
     */
    @GetMapping("findByAuthor")
    public List<ReturnDoc> findDocumentByAuthors(Set<String> authors){
        return documentService.findDocumentByAuthors(authors);
    }

    /**
     * <pre>通过某一段落的内容来查找文档</pre>
     * @param content 某一段落中的内容
     * @return 结果集
     */
    @GetMapping("findByContent")
    public List<ReturnDoc> findDocumentByParagraphContent(String content){
        List<Paragraph> paragraphs = paragraphService.findByContent(content);
        List<ReturnDoc> list = new ArrayList<>();
        for (Paragraph p :
                paragraphs) {
            Document document = documentService.findById(Collections.singletonList(p.getId() / 10000));
            list.add(new ReturnDoc(document, (int) (p.getId()%10000),p.getContent()));
        }
        return null;
    }


}
