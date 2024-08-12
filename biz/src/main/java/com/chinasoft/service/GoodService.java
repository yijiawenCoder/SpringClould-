package com.chinasoft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chinasoft.entity.Good;


/**
* @author 26510
* @description 针对表【good(商品信息表)】的数据库操作Service
* @createDate 2024-08-12 16:15:55
*/
public interface GoodService extends IService<Good> {
   void insertGood(Good good);
}
