package com.chinasoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinasoft.entity.Good;
import org.mybatis.spring.annotation.MapperScan;


/**
* @author 26510
* @description 针对表【good(商品信息表)】的数据库操作Mapper
* @createDate 2024-08-12 16:15:55
* @Entity generator.domain.Good
*/
@MapperScan
public interface GoodMapper extends BaseMapper<Good> {

}




