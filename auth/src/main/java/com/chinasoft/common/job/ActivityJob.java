package com.chinasoft.common.job;

import com.chinasoft.common.mq.reject.ApplyNotify;
import com.chinasoft.entity.SysUser;
import com.chinasoft.mapper.SysUserMapper;
import com.chinasoft.shiro.util.MyLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ActivityJob {
    protected static final MyLog _log =MyLog.getLog(ApplyNotify.class);
    @Resource
    SysUserMapper userMapper;
    @Scheduled(fixedDelay = 10000)
    public void doJob(){
        //_log.info("定时任务执行");
        List<SysUser> sysUsers = userMapper.selectList(null);
        sysUsers.forEach(sysUser ->{
            _log.info("start job userName = {}", sysUser.getUserName());
        });
    }
}
