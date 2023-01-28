package com.medicalretrieval.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Set;

/**
 * <pre>
 *     这个实体类主要是为了应对精准到页码的搜索
 * </pre>
 * @author 梁宏凯
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "paragraph",shards = 3,replicas = 2)
public class Paragraph {
    /**
     * <pre>
     *     此处的id是pdf的id+页码,(页码占四位，最大可到9999)<br/>
     *     若id = 100010<br/>
     *     则pdf的id为10。页码是0010,也就是10
     * </pre>
     */
    @Id
    private Long id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;
    @Field(type = FieldType.Object)
    private Set<ImgInfo> imgInfos;
}
