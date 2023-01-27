package com.medicalretrieval.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImgInfo {
    @Id
    private Long id;
    @Field(index = false)
    private String ImgAddress;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String ImgText;
}
