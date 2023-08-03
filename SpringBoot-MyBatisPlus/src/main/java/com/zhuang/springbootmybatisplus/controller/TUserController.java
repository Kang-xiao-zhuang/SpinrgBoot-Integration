package com.zhuang.springbootmybatisplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuang.springbootmybatisplus.entity.TUser;
import com.zhuang.springbootmybatisplus.mapper.TUserMapper;
import com.zhuang.springbootmybatisplus.service.TUserService;
import com.zhuang.springbootmybatisplus.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-08-03
 */
@RestController
@RequestMapping("/user")
public class TUserController {

    @Autowired
    private TUserMapper userMapper;

    @Autowired
    private TUserService userService;


    @GetMapping("/findOne/{id}")
    public Result findOne(@PathVariable Integer id) {
        TUser user = userMapper.selectById(id);
        return new Result(true, user, "操作成功");
    }

    @GetMapping("/findAll")
    public Result findAll() {
        List<TUser> list = userService.list();
        return new Result(true, list, "操作成功");
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody TUser user) {
        return userMapper.insert(user) == 1 ? new Result(true, user, "操作成功") : new Result(false, user, "操作失败");
    }

    @DeleteMapping("/deleteUser/{id}")
    public Result deleteUserById(@PathVariable Integer id){
        return userMapper.deleteById(id) == 1 ? new Result(true, userMapper.selectList(null), "删除成功") : new Result(false, userMapper.selectList(null), "删除失败");
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody TUser user){
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getUid());
        TUser update = userService.getOne(wrapper);
        update.setUname(user.getUname());
        update.setUage(user.getUage());
        update.setUgender(user.getUgender());
        userService.updateById(update);
        return new Result(true,update,"更新成功");
    }
}

