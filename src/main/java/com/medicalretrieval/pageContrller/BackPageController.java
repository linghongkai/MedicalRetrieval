package com.medicalretrieval.pageContrller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/back")
public class BackPageController {
    @GetMapping("/pdf")
    String b(){
        return "ENSITE_NAVX和双LAS_省略_左心房线性消融治疗阵发性心房颤动_陈明龙";
    }
}
