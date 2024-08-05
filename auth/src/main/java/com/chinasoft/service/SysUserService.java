package com.chinasoft.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chinasoft.common.util.PageUtils;
import com.chinasoft.common.util.R;
import com.chinasoft.dto.UserLoginRequest;
import com.chinasoft.entity.SysUser;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


/**
* @author 26510
* @description 针对表【sys_user(系统管理员)】的数据库操作Service
* @createDate 2024-08-05 13:53:54
*/
public interface SysUserService extends IService<SysUser> {
    List<SysUser> permission(UserLoginRequest userLoginRequest);
    R register( SysUser user);

    R createToken(SysUser user);
    IPage<SysUser> getUsers(Map<String,Object> params);



}
