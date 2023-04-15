package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.user.User;
import com.medicalretrieval.service.UserService;
import com.medicalretrieval.utils.Page4Navigator;
import com.medicalretrieval.utils.Result;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/find")
    Page4Navigator<User> findAll(@RequestParam(name = "start",defaultValue = "1") int start,@RequestParam(name = "pageSize",defaultValue = "10") int pageSize){
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start-1,pageSize,sort);
        Page<User> all = userService.findAll(pageable);
        System.out.println(all);
        Page4Navigator<User> users = new Page4Navigator<>(all,5);
        return users;
    }


    @PutMapping("/disabled/{id}")
    Object disabled(@PathVariable String id,int disabled){
        int line = userService.updateDisabledById(disabled,Long.parseLong(id));
        if (line==1){
            return Result.success();
        }
        return Result.fail("更改失败");
    }

    @PutMapping("/permission/{id}")
    Object permission(@PathVariable String id,int permission){
        int line = userService.updatePermissionGroupIdById(permission,Long.parseLong(id));
        if (line == 1){
            return Result.success();
        }
        return Result.fail("权限更改失败");
    }




}
