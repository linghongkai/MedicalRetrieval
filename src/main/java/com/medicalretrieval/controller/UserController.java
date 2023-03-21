package com.medicalretrieval.controller;

import com.medicalretrieval.pojo.user.User;
import com.medicalretrieval.service.UserService;
import com.medicalretrieval.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    User addUser(@RequestBody User user){
        if (user.getSex()==1){
            user.setAvatar("http://192.168.43.144:8080/avatar/default_avatar_female.png");
        }
        System.out.println(user);
        userService.save(user);
        System.out.println("添加成功："+user);
        return user;
    }

    @DeleteMapping("/")
    Object deleteUser(@RequestBody User user){
        userService.deleteById(user.getId());
        System.out.println("删除成功："+user);
        return Result.success();
    }

    @PutMapping("/")
    Object updateUser(@RequestBody User user){
        userService.updatePasswordAndEmailAndTelephoneAndPermissionGroupIdAndAvatarAndDisabledById(user.getPassword(),user.getEmail(),user.getTelephone(), user.getPermissionGroupId(), user.getAvatar(), user.getDisabled(), user.getId());
        System.out.println("修改成功："+user);
        return Result.success();
    }

    @GetMapping("/")
    Object queryUser(String account,String password){
        User user = userService.findByAccountAndPassword(account,password);
        if (user==null){
            return Result.fail("登录失败，用户名或密码错误");
        }
        System.out.println("查询成功："+user);
        return Result.success(user);
    }




}
