package com.devglan.config;

import com.devglan.consumer.CustomerInfoSubscriber;
import com.devglan.producer.CustomerInfoPublisher;
import com.devglan.producer.RedisCustomerInfoPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.Executors;

//@SpringBootConfiguration
@Configuration
//@EnableTransactionManagement
public class RedisConfig {


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        //jedisConnectionFactory.setHostName("172.17.0.4");
        //jedisConnectionFactory.setHostName("myredis");
        return jedisConnectionFactory ;
    }



    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        //template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        //template.setEnableTransactionSupport(true);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager() throws APIException {
//        return new DataSourceTransactionManager(dataConfiguration.dataSource());
//    }


    @Bean
    MessageListener messageListener(){
        return new CustomerInfoSubscriber();
    }
    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(messageListener());
    }

    @Bean
    RedisMessageListenerContainer redisContainer() {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        container.setTaskExecutor(Executors.newFixedThreadPool(4));
        return container;
    }

    @Bean
    CustomerInfoPublisher redisPublisher() {
        return new RedisCustomerInfoPublisher(redisTemplate(), topic());
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:jsa-channel");
    }

}
