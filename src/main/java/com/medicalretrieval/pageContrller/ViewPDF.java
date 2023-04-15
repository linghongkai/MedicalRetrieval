package com.medicalretrieval.pageContrller;


import com.medicalretrieval.api.oss.OssDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class ViewPDF {

    @Autowired
    private OssDao ossDao;
//    @GetMapping("/view")
//    public String viewPDF(String url, String name) throws Exception {
//        ossDao.download(url, name);
//        System.out.println("下载成功");
//        return "./src/main/resources/static/PDF/"+name;
//    }

    @GetMapping("/view")
    public ResponseEntity<Resource> viewPDF(String url, String name,String type) throws Exception {
        // 下载 PDF 文件
        File pdfFile = ossDao.download(url, name);
        // 构建 Resource 对象
        Resource resource = new FileSystemResource(pdfFile);
        System.out.println(name);
        // 返回 ResponseEntity 对象
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, type.equals("inline")?type:"attachment; filename=\"" + name + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
