package com.chinasoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinasoft.common.util.MybatisRedisCache;
import com.chinasoft.entity.SysUser;
import org.apache.ibatis.annotations.CacheNamespace;
import org.mybatis.spring.annotation.MapperScan;


/**
* @author 26510
* @description 针对表【sys_user(系统管理员)】的数据库操作Mapper
* @createDate 2024-08-05 13:53:54
* @Entity generator.domain.SysUser
*/

@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
@MapperScan
public interface SysUserMapper extends BaseMapper<SysUser> {

}




