package com.chinasoft.feign.fallback;

import com.chinasoft.common.util.R;
import com.chinasoft.feign.BizFeignService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BizFeignServiceFallback implements BizFeignService {
    @Override
    public R add(Map<String, Object> param) {
        return null;
    }
}
