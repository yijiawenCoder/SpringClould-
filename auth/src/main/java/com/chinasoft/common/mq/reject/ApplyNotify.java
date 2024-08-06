package com.chinasoft.common.mq.reject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinasoft.shiro.util.MyLog;
import org.springframework.stereotype.Component;


public abstract class ApplyNotify {
    protected static final MyLog _log =MyLog.getLog(ApplyNotify.class);
    //直接发送
    public abstract void send (String msg);
    //延时发送
    public abstract void send (String msg,long delay);
    public void receive (String msg){
        _log.info("do notify task, msg={}",msg);
        JSONObject object = JSON.parseObject(msg);
        String mobile = object.getString("mobile");
        String info = object.getString("info");
        _log.info("处理消息给"+mobile+",info="+info);

    }
}
