package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.Document;
import com.medicalretrieval.service.DocumentService;
import org.elasticsearch.common.inject.Inject;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <pre>
 *     对于文档的控制
 * </pre>
 * @author 梁宏凯
 */
@RequestMapping("/item/")
@RestController
public class DocumentController {
    @Inject
    DocumentService documentService;

    /**
     * <pre>批量保存pdf</pre>
     * @param documents 批量的文档
     * @return 提交结果
     */
    @PostMapping("saveBatch")
    public String saveBatch(@RequestBody List<Document> documents){
        if(CollectionUtils.isEmpty(documents)){
            return "文档不能为空";
        }
        documentService.saveAll(documents);
        return "文档提交成功";
    }


}
