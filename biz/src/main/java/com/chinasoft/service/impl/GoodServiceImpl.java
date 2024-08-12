package com.chinasoft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.chinasoft.entity.Good;
import com.chinasoft.mapper.GoodMapper;
import com.chinasoft.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @author 26510
 * @description 针对表【good(商品信息表)】的数据库操作Service实现
 * @createDate 2024-08-12 16:15:55
 */
@Service
@Transactional
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good>
        implements GoodService {
    @Autowired
    GoodMapper goodMapper;

    @Override
    public void insertGood(Good good) {
        good.setState(1);
        good.setOptrdate(new Timestamp(new java.util.Date().getTime()));
        goodMapper.insert(good);

    }
}




