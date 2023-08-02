package com.zhuang.springbootmybatis.controller;

import com.zhuang.springbootmybatis.entity.User;
import com.zhuang.springbootmybatis.service.UserService;
import com.zhuang.springbootmybatis.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //查找一个用户
    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable Integer id) {
        User user = userService.findOne(id);
        return new Result(true, user, "操作成功");
    }

    //查找所有用户
    @GetMapping("/findAllUser")
    public Result findAll() {
        List<User> users = userService.findAll();
        return new Result(true, users, "操作成功");
    }

    //添加一个用户
    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        Integer rows = userService.addUser(user);
        if (rows > 0) {
            return new Result(true, rows, "操作成功");
        } else {
            return new Result(false, rows, "操作失败");
        }
    }

    //删除一个用户
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        Integer rows = userService.deleteById(id);
        if (rows > 0) {
            return new Result(true, rows, "操作成功");
        } else {
            return new Result(false, rows, "操作失败");
        }
    }

    //修改一个用户
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        Integer rows = userService.updateById(user);
        if (rows > 0) {
            return new Result(true, rows, "操作成功");
        } else {
            return new Result(false, rows, "操作失败");
        }
    }
}
