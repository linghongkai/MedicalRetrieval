package com.medicalretrieval;

import com.medicalretrieval.pojo.user.User;
import com.medicalretrieval.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.jws.soap.SOAPBinding;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class MedicalRetrievalApplicationTests {
    @Autowired
    UserService userService;
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

    @Test
    void addUser(){
        List<User> users = new ArrayList<>();
        for (int i=1;i<30;i++){
            User user = new User();
            user.setAccount(String.valueOf(i));
            user.setPassword(String.valueOf(i));
            user.setEmail("email"+i);
            user.setDisabled(0);
            user.setTelephone("telephone"+i);
            user.setPermissionGroupId(0);

            users.add(user);
        }
        userService.saveAll(users);
        /*User user = new User();
        user.setPassword("4444");
        userService.save(user);*/
    }

}
