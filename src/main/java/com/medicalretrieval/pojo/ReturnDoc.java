package com.medicalretrieval.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>主要是针对显示指定页数的pdf，当通过内容搜索时，可能会要求从第3页的pdf显示</pre>
 * @author 梁宏凯
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnDoc {
    private Document document;
    private int page;
}
