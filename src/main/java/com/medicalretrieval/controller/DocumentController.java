package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.pojo.PageContent;
import com.medicalretrieval.pojo.Paragraph;
import com.medicalretrieval.pojo.ReturnDoc;
import com.medicalretrieval.service.DocumentService;

import com.medicalretrieval.service.ParagraphService;
import com.medicalretrieval.utils.PDFUtils;
import com.medicalretrieval.utils.Page4Navigator;
import com.medicalretrieval.utils.Transition;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wiremock.org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

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
            PDFUtils.ReadPDFText(d,paragraphs);
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
        PDFUtils.ReadPDFText(document,paragraphs);
        paragraphService.saveAll(paragraphs);
        return true;
    }


    /**
     * <pre>通过文章标题查找文档</pre>
     * @param title 文章标题
     * @return 文档类的链表
     */
    @GetMapping("findByTitle")
    public Page4Navigator<ReturnDoc> findDocumentByTitle(@RequestParam(value = "title") String title,@RequestParam(value = "authors")List<String>authors,@RequestParam(value = "content")String content,@RequestParam(value = "current",defaultValue = "1")int current){
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(title)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",title));
        }
        if(authors!=null){
            boolQueryBuilder.must(QueryBuilders.matchQuery("author",authors));
        }
        if(StringUtils.isNotBlank(content)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("content",content));
        }
        query.withQuery(boolQueryBuilder);

        Pageable pageable = PageRequest.of(current-1,10);



        SearchHits<Document> searchHits = elasticsearchRestTemplate.search(query.build(),Document.class, IndexCoordinates.of("document"));
        searchHits.getTotalHits();
        List<ReturnDoc> list = new ArrayList<>();

        for (SearchHit<Document>searchHit:searchHits){
            ReturnDoc returnDoc = new ReturnDoc();
            returnDoc.setDocument(searchHit.getContent());
            returnDoc.setPage(1);
            returnDoc.setAbstract("");
            returnDoc.setScore(searchHit.getScore());

            list.add(returnDoc);
        }

        Page<ReturnDoc> page = new PageImpl<>(list, pageable, searchHits.getTotalHits());

        return new Page4Navigator<>(page, 5);

    }



}
