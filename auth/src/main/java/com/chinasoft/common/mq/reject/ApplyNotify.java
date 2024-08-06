package com.chinasoft.common.mq.reject;

import com.chinasoft.shiro.util.MyLog;

public abstract class ApplyNotify {
    public static final MyLog _log =MyLog.getLog(ApplyNotify.class);
    //直接发送
    public abstract void send (String msg);
    //延时发送
    public abstract void send (String msg,long delay);
    public void receive (String msg){
        _log.info("do notify task, msg={}",msg);
    }
}
