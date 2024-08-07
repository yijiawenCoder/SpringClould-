package com.chinasoft.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 使用redis实现Mybatis Plus二级缓存
 *
 * @Author yijiawenCoder
 * @Date 2021/7/24 10:42
 */

public class MybatisRedisCache implements Cache {


    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private RedisTemplate redisTemplate;

    private RedisTemplate getRedisTemplate(){
        //通过ApplicationContextHolder工具类获取RedisTemplate
        if (redisTemplate == null) {
            redisTemplate = (RedisTemplate) ApplicationContextHolder.getBeanByName("redisTemplate");
        }
        return redisTemplate;
    }

    private final String id;

    public MybatisRedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        //使用redis的Hash类型进行存储
        getRedisTemplate().opsForHash().put(id,key.toString(),value);
    }

    @Override
    public Object getObject(Object key) {
        try {
            //根据key从redis中获取数据
            return getRedisTemplate().opsForHash().get(id,key.toString());
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (key != null) {
            getRedisTemplate().delete(key.toString());
        }
        return null;
    }

    @Override
    public void clear() {

        Set<String> keys = getRedisTemplate().keys("*:" + this.id + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            getRedisTemplate().delete(keys);
        }
    }

    @Override
    public int getSize() {
        Long size = (Long) getRedisTemplate().execute((RedisCallback<Long>) RedisServerCommands::dbSize);
        return size.intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}