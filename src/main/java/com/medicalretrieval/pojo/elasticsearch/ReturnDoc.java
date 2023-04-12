package com.medicalretrieval.pojo.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>主要是针对显示指定页数的pdf，当通过内容搜索时，可能会要求从第3页的pdf显示</pre>
 * @author 梁宏凯
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnDoc {

    private Document document;//文档
    private int page;//当前应定位到的页数
    private List<String> Abstract;//摘要
    private float score;
}
