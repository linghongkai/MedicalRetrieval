package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.elasticsearch.Document;
import com.medicalretrieval.pojo.elasticsearch.Paragraph;
import com.medicalretrieval.pojo.elasticsearch.ReturnDoc;
import com.medicalretrieval.pojo.elasticsearch.SearchDoc;
import com.medicalretrieval.service.DocumentService;

import com.medicalretrieval.service.ParagraphService;
import com.medicalretrieval.utils.PDFUtils;
import com.medicalretrieval.utils.Page4Navigator;
import com.medicalretrieval.utils.Transition;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.SearchContextHighlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wiremock.org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
    public boolean saveBatch(@RequestBody MultipartFile[] multipartFiles) throws IOException {
        List<Document> documents = new ArrayList<>();
        List<File> files = Transition.TransitionDocument(multipartFiles,documents);
        if(CollectionUtils.isEmpty(documents)){
            return false;
        }

        List<Paragraph> paragraphs = new ArrayList<>();
        int cnt = 0;
        for (Document d :
                documents) {


            PDFUtils.ReadPDFText(files.get(cnt++),d,paragraphs);
            files.remove(0);
        }
        documentService.saveAll(documents);
        paragraphService.saveAll(paragraphs);
        return true;
    }

    /**
     * <pre>保存一个数据</pre>
     * @param file 文档类
     * @return 提交结果
     */
    @PostMapping("saveOne")
    public boolean saveOne(@RequestBody MultipartFile file) throws IOException {

        Document document =new Document();
        File file1 = Transition.TransitionDocument(file,document);
        List<Paragraph> paragraphs = new ArrayList<>();
        System.out.println(document);
        PDFUtils.ReadPDFText(file1,document,paragraphs);
        paragraphService.saveAll(paragraphs);
        System.out.println("传输成功！！！！！");
        System.out.println(document);
        documentService.saveOne(document);
        return true;
    }


    /**
     * <pre>通过文章标题查找文档</pre>
     * @param title 文章标题
     * @return 文档类的链表
     */
    @GetMapping("findByTitle")
    public Page4Navigator<ReturnDoc> findDocumentByTitle(
                                                         @RequestParam(value = "title",defaultValue = "") String title,
                                                         @RequestParam(value = "current",defaultValue = "1")int current
                                                        )
    {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.isNotBlank(title)){
            boolQueryBuilder.must(QueryBuilders.matchQuery("title",title));
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
            returnDoc.setScore(searchHit.getScore()*100);

            list.add(returnDoc);
        }

        Page<ReturnDoc> page = new PageImpl<>(list, pageable, searchHits.getTotalHits());

        return new Page4Navigator<>(page, 5);

    }

    private void createQuery(BoolQueryBuilder queryBuilder, String subject, String searchTypeOf, String linkOf,String fieldName){
        if(subject!=null && subject.length()>0){
            if("precision".equals(searchTypeOf)){
                //精确
                if("and".equals(linkOf)){
                    queryBuilder.must(QueryBuilders.matchQuery(fieldName,subject));
                }else if("or".equals(linkOf)){
                    queryBuilder.should(QueryBuilders.matchQuery(fieldName,subject));
                }else{
                    queryBuilder.mustNot(QueryBuilders.matchQuery(fieldName,subject));
                }
            }else{
                //模糊
                if("and".equals(linkOf)){
                    queryBuilder.must(QueryBuilders.fuzzyQuery(fieldName,subject).fuzziness(Fuzziness.AUTO));
                }else if("or".equals(linkOf)){
                    queryBuilder.should(QueryBuilders.fuzzyQuery(fieldName,subject).fuzziness(Fuzziness.AUTO));
                }else{
                    queryBuilder.mustNot(QueryBuilders.fuzzyQuery(fieldName,subject).fuzziness(Fuzziness.AUTO));
                }
            }
        }
    }

    @PostMapping("advancedSearch")
    public Page4Navigator<ReturnDoc> advanceSearch(@RequestBody SearchDoc searchDoc){

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

//        boolQueryBuilder.must(QueryBuilders.matchQuery("title",searchDoc.getTitle()))
        createQuery(boolQueryBuilder,searchDoc.getTitle(),searchDoc.getSearchTypeOfTitle(),searchDoc.getLinkOfTitle(),"title");
        createQuery(boolQueryBuilder,searchDoc.getAuthor(),searchDoc.getSearchTypeOfAuthor(),searchDoc.getLinkOfAuthor(),"author");
        createQuery(boolQueryBuilder,searchDoc.getContent(),searchDoc.getSearchTypeOfContent(),searchDoc.getLinkOfContent(),"pageContents");
        builder.withQuery(boolQueryBuilder);
        SearchHits<Document> searchHits = elasticsearchRestTemplate.search(builder.build(), Document.class, IndexCoordinates.of("document"));


        builder = new NativeSearchQueryBuilder();
        boolQueryBuilder = QueryBuilders.boolQuery();
        createQuery(boolQueryBuilder,searchDoc.getContent(),searchDoc.getSearchTypeOfContent(),searchDoc.getLinkOfContent(),"content");
        createQuery(boolQueryBuilder,searchDoc.getContent(),searchDoc.getSearchTypeOfContent(),searchDoc.getLinkOfContent(),"imgInfos");

        builder.withHighlightFields(
                new HighlightBuilder.Field("content")
                        .preTags("")
                        .postTags("")
                        .fragmentSize(50)
                ,new HighlightBuilder.Field("imgInfos")
                        .preTags("")
                        .postTags("")
                        .fragmentSize(50)
        );
        builder.withQuery(boolQueryBuilder);
        SearchHits<Paragraph> paragraph = elasticsearchRestTemplate.search(builder.build(), Paragraph.class, IndexCoordinates.of("paragraph"));
        Map<Long,List<String>> map = new HashMap<>();
        for (SearchHit<Paragraph> p:paragraph){
            Long id = p.getContent().getId();
            List<String> field = p.getHighlightField("content");
            field.addAll(p.getHighlightField("imgInfos"));
            if (map.containsKey(id/10000)){
                map.put(id/10000,field);
            }else {
                map.get(id/10000).addAll(field);
            }
        }
        Pageable pageable = PageRequest.of(searchDoc.getCurrent()-1, searchDoc.getPageSize(), Sort.by(Sort.Direction.DESC,"score"));

        List<ReturnDoc> list = new ArrayList<>();
        for(SearchHit<Document> d:searchHits){
            ReturnDoc returnDoc = new ReturnDoc();
            returnDoc.setDocument(d.getContent());
            returnDoc.setPage(1);
            returnDoc.setScore(d.getScore());
            returnDoc.setAbstract(map.get(d.getContent().getId()));
            list.add(returnDoc);
        }

        Page<ReturnDoc> page = new PageImpl<>(list,pageable, searchHits.getTotalHits());
        return new Page4Navigator<>(page,5);
    }

    @GetMapping("find")
    public List<PdfRestInfo> find(@RequestParam(value = "title",defaultValue = "") String title,@RequestParam(value = "authors",defaultValue = "")List<String>authors,@RequestParam(value = "content",defaultValue = "")String content,@RequestParam(value = "current",defaultValue = "1")int current){
        List<PdfRestInfo> pdfRestInfoList = new ArrayList<>();
        List<ReturnDoc> returnDocList = findDocumentByTitle(title, current).getContent();
        for (ReturnDoc r:returnDocList){
            PdfRestInfo pdfRestInfo = new PdfRestInfo();

            pdfRestInfo.setAuthors(r.getDocument().getAuthor().toString().replaceAll(",|\\[|]",""));
            pdfRestInfo.setFilePath(r.getDocument().getUrl());
            pdfRestInfo.setName(r.getDocument().getTitle());
            String str = "";
            for (String s:r.getAbstract()){
                str=str+s+'\n';

            }
            pdfRestInfo.setTextSummary(str);

            pdfRestInfoList.add(pdfRestInfo);
        }
        return pdfRestInfoList;
    }

    private class PdfRestInfo
    {
        private String filePath;

        private String name;

        private String authors;
        private String TextSummary;


        public String getAuthors() {
            return authors;
        }

        public void setAuthors(String authors) {
            this.authors = authors;
        }



        public void setFilePath(String filePath){
            this.filePath = filePath;
        }
        public String getFilePath(){
            return this.filePath;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setTextSummary(String TextSummary){
            this.TextSummary = TextSummary;
        }
        public String getTextSummary(){
            return this.TextSummary;
        }
    }





}
