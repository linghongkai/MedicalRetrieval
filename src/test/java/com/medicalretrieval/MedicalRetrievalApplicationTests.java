package com.medicalretrieval;

import com.medicalretrieval.pojo.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;



@SpringBootTest
class MedicalRetrievalApplicationTests {
    @Test
    void contextLoads() {

        try {
            String urlString = "https://medical-retrieval.oss-cn-beijing.aliyuncs.com/影响人工关节置换术后下肢深静脉血栓形成的临床风险因素分析_关振鹏.pdf"; // 替换成需要生成的网址
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Disposition", "inline"); // 替换成需要设置的请求头信息
            String urlWithHeaders = connection.getURL().toString();
            System.out.println(urlWithHeaders); // 将生成的网址打印输出
        } catch (Exception e) {
            e.printStackTrace();
        }
//        {
//            Document document;
//        }//document 销毁，生命结束


    }

}
