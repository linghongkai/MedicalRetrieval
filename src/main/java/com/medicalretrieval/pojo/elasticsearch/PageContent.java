package com.medicalretrieval.pojo.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageContent {

    //id表示页码，从1开始
    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;//文章内容

    @Field(type = FieldType.Object)
    private List<ImgInfo> imgInfos;
}
