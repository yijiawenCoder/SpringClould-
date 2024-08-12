package com.chinasoft.feign;

import com.chinasoft.common.aspect.FeignHeaderInterceptor;
import com.chinasoft.common.util.R;
import com.chinasoft.feign.fallback.BizFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
//biz所有服务都只能在这一个类里面

@FeignClient(value = "biz",
        configuration = FeignHeaderInterceptor.class,
        fallback = BizFeignServiceFallback.class)
public interface BizFeignService {

    @PostMapping("/biz/good/add")
     R add(@RequestParam Map<String, Object> param );
}
