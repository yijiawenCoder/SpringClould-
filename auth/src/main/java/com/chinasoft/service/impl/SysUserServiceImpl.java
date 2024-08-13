package com.chinasoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.chinasoft.common.mq.reject.ApplyNotify;
import com.chinasoft.common.mq.reject.impl.ApplyNotifyImpl;
import com.chinasoft.common.util.PageUtils;
import com.chinasoft.common.util.R;
import com.chinasoft.common.util.RedisUtil;
import com.chinasoft.common.util.TokenGenerator;
import com.chinasoft.dto.UserLoginRequest;
import com.chinasoft.entity.SysUser;
import com.chinasoft.mapper.SysUserMapper;
import com.chinasoft.service.SysUserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author 26510
 * @description 针对表【sys_user(系统管理员)】的数据库操作Service实现
 * @createDate 2024-08-05 13:53:54
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {
    @Resource
    SysUserMapper userMapper;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    RedisUtil redisUtil;
    @Resource
    private ApplyNotify applyNotify;


    @Override
    public List<SysUser> permission(UserLoginRequest userLoginRequest) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userLoginRequest.getUserName());
        List<SysUser> sysUsers = userMapper.selectList(queryWrapper);
        return sysUsers;
    }
    //不知道为什么注入失败

    @Override
    public R register(SysUser user) {
        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        user.setOptrdate(new Timestamp(new java.util.Date().getTime()));
        userMapper.insert(user);


        Map<String, String> params = new HashMap<>();
        params.put("mobile", user.getMobile());
        params.put("info", "*******注册成功********");
        Gson gson = new Gson();

        applyNotify.send(gson.toJson(params), 10);
        return R.ok();
    }

    @Override
    public R createToken(SysUser user) {
        String token = TokenGenerator.generateValue();
        //单点登录
        if (redisUtil.getAll("*") != null) {
            Set<Object> keys = redisUtil.getAll("*");
            for (Object k : keys) {
                try {
                    if (redisUtil.get(k.toString()).toString().equals(user.getUserId())) {
                        redisUtil.del(k.toString());
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        //shiro授权

        Set<String>permissions =new HashSet<>();
        //在redis捆绑之前把权限放在permissions里面
        user.setPermissions(permissions);

        //捆绑登陆人信息和id
        redisUtil.set(token, user.getUserId(), 3600);
        redisUtil.set(user.getUserId(), user, 3600);
        return R.ok().put("token", token);
    }

    @Override
    public IPage<SysUser> getUsers(Map<String, Object> params) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", params.get("userName"));
        int current = Integer.parseInt(params.get("page").toString());
        int size = Integer.parseInt(params.get("limit").toString());
        Page<SysUser> page = new Page<>(current, size);

        return userMapper.selectPage(page, queryWrapper);
    }
}




