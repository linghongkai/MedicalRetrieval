package com.medicalretrieval.pojo.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;
import java.util.Set;


/**
 * 实体类，对标一份医学文档
 * @author 梁宏凯
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@org.springframework.data.elasticsearch.annotations.Document(indexName = "document",shards = 3,replicas = 2)
public class Document {
    /**
     * id 唯一标识
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;//整个文档的标题

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private Set<String> author;//作者,多个作者用英文逗号拼接


    @Field(type = FieldType.Date,format = DateFormat.year_month_day)
    private Date date;//发布时间   yyyy-MM-dd

    @Field(type = FieldType.Integer,index = false)
    private Integer pageSize;//页面数量

    @Field(type = FieldType.Nested)
    private Set <PageContent> pageContents;//页面内容

    @Field(index = false)
    private String url;
}
