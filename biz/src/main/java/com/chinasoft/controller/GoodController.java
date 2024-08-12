package com.chinasoft.controller;

import com.chinasoft.common.util.R;
import com.chinasoft.entity.Good;
import com.chinasoft.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodService goodService;
    @PostMapping("/save")
    public R insert(@RequestBody Good good) {
         goodService.insertGood(good);
         return R.ok();
    }

    @PostMapping("/add")
    public R add(@RequestParam Map<String, Object> param ) {
        Good good = new Good();
        good.setGname( param.get("name").toString());
        goodService.insertGood(good);
        return R.ok();
    }
}
