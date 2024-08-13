package com.chinasoft.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinasoft.common.mq.reject.ApplyNotify;
import com.chinasoft.common.util.PageUtils;
import com.chinasoft.common.util.R;
import com.chinasoft.dto.UserLoginRequest;
import com.chinasoft.entity.SysUser;
import com.chinasoft.feign.BizFeignService;
import com.chinasoft.service.SysUserService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
   private PasswordEncoder passwordEncoder;
    @Resource
    private  SysUserService userService;
    @Resource
    private BizFeignService bizFeignService;

    @PostMapping("/login")
    public R login(@RequestBody UserLoginRequest userLoginRequest){
        //能否登录
        List<SysUser> users = userService.permission(userLoginRequest);
       if( passwordEncoder.matches(userLoginRequest.getUserPass(), users.get(0).getUserPass())){
          //授权
           return userService.createToken(users.get(0));
       }
        return R.error("错误");

    }
    @PostMapping("/register")
    public  R register(@RequestBody SysUser user){
        userService.register(user);
         return R.ok();
    }
    @GetMapping("/list")
    public IPage<SysUser> getUserList (@RequestParam Map<String,Object> params){

        return   userService.getUsers(params);
    }
@GlobalTransactional
    @PostMapping("/test")
    public R test(@RequestParam  Map<String,Object> params){
        bizFeignService.add(params);
        return R.ok();

    }
}
