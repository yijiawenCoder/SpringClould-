package com.chinasoft.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author yijiawenCoder
 */
@Configuration
public class SecurityCoreConfig {

    /**
     * 这里导入的bean：LettuceConnectionFactory，在其它模块中生成
     */
    @Resource
    private LettuceConnectionFactory redisConnectionFactory;
//spring提供的的加密器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//redis去掉双引号
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
/*@Bean
public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory)
{
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
    *//**
     * SpringBoot扩展了ClassLoader，进行分离打包的时候，使用到JdkSerializationRedisSerializer的地方
     * 会因为ClassLoader的不同导致加载不到Class
     * 指定使用项目的ClassLoader
     *
     * JdkSerializationRedisSerializer默认使用{@link sun.misc.Launcher.AppClassLoader}
     * SpringBoot默认使用{@link org.springframework.boot.loader.LaunchedURLClassLoader}
     *//*
    ClassLoader classLoader = this.getClass().getClassLoader();
    stringRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer(classLoader));
    stringRedisTemplate.afterPropertiesSet();
    return stringRedisTemplate;
}*/
}

