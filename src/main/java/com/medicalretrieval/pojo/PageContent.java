package com.medicalretrieval.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageContent {

    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;//文章内容

    @Field(type = FieldType.Object)
    private Set<ImgInfo> imgInfos;
}
