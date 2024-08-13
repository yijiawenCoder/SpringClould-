/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.chinasoft.shiro.realm;

import com.chinasoft.common.util.RedisUtil;
import com.chinasoft.entity.SysUser;
import com.chinasoft.shiro.oauth2.OAuth2Token;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认证
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if(principals.getPrimaryPrincipal() instanceof SysUser){
            SysUser user = (SysUser)principals.getPrimaryPrincipal();
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.setStringPermissions(user.getPermissions());
            return info;
        }
        else{
            return null;
        }
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();
        //根据accessToken，查询用户信息
        //SysUserToken tokenEntity = tokenService.findSysUserTokenByToken(accessToken);
        //token失效
        if(!redisUtil.hasKey(accessToken)){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        //查询用户信息
        if(redisUtil.get(redisUtil.get(accessToken).toString()) instanceof  SysUser){
            SysUser user = (SysUser)redisUtil.get(redisUtil.get(accessToken).toString());
            //账号锁定
            if(user.getState()==0){
                throw new LockedAccountException("账号已被冻结,请联系管理员");
            }
            if(user.getState()==-1){
                throw new LockedAccountException("账号已被注销,请联系管理员");
            }
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
            return info;
        }
        else{
            return null;
        }
    }
}
