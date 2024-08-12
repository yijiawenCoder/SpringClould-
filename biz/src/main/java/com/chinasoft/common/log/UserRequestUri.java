package com.chinasoft.common.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserRequestUri extends ClassicConverter {
//获取用户请求路由
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            return sra.getRequest().getRequestURI();
        }catch (Exception e){
            return null;
        }


    }
}
