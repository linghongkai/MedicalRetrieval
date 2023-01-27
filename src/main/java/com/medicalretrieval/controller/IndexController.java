package com.medicalretrieval.controller;


import org.elasticsearch.common.inject.Inject;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>对于elasticsearch索引的操作</pre>
 * @author 梁宏凯
 */
@RequestMapping("/index/")
@RestController
public class IndexController {
    @Inject
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 创建索引
     * @param indexName 索引名
     * @return 创建结果
     */
    @GetMapping("create")
    public String create(@RequestParam String indexName){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName));
        if(indexOperations.exists()){
            return "索引已存在";
        }
        indexOperations.create();
        return "索引创建成功";
    }

    /**
     * 删除索引
     * @param indexName 索引名
     * @return 删除结果
     */
    @GetMapping("delete")
    public String delete(@RequestParam String indexName){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName));
        indexOperations.delete();
        return "索引删除成功";
    }
}
