package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.pojo.ReturnDoc;
import com.medicalretrieval.service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * <pre>批量保存pdf</pre>
     * @param documents 批量的文档
     * @return 提交结果
     */
    @PostMapping("saveBatch")
    public boolean saveBatch(@RequestBody List<Document> documents){
        if(CollectionUtils.isEmpty(documents)){
            return false;
        }
        documentService.saveAll(documents);
        return true;
    }

    /**
     * <pre>保存一个数据</pre>
     * @param document 文档类
     * @return 提交结果
     */
    @PostMapping("saveOne")
    public boolean saveOne(@RequestBody Document document){
        if(document==null){
            return false;
        }
        documentService.saveOne(document);
        return true;
    }


    /**
     * <pre>通过文章标题查找文档</pre>
     * @param title 文章标题
     * @return 文档类的链表
     */
    public List<ReturnDoc> findDocumentByTitle(String title){
        return documentService.findDocumentByTitle(title);
    }


    /**
     * <pre>通过作者查找文档</pre>
     * @param authors 作者集
     * @return 文档结果
     */
    public List<ReturnDoc> findDocumentByAuthors(Set<String> authors){
        return documentService.findDocumentByAuthors(authors);
    }


}
