package com.tplink.blog;

import com.alibaba.fastjson.JSONObject;
import com.tplink.blog.service.ThreadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;

/**
 * @author xyd
 * @create 2023-02-25 20:35
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "xiaoming");
        jsonObject.put("age", 20);
        jsonObject.put("job", "student");

        // 保存到redis
        redisTemplate.opsForValue().set("stu1", jsonObject.toJSONString());
        System.out.println(redisTemplate.opsForValue().get("stu1"));

    }


    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void print(String threadName) {
        System.out.println("线程名：" + threadName + " 线程变量：" + threadLocal.get());
        // 移除线程变量
        threadLocal.remove();
    }


    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("local1");
                print("线程1");
                System.out.println("after remove:" + threadLocal.get());
            }
        }, "线程1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("local2");
                print("线程2");
                System.out.println("after remove:" + threadLocal.get());
            }
        }, "线程2").start();

    }


    @Autowired
    private ThreadService threadService;

    @Test
    public void testThreadPoolUse() {
        threadService.useThreadPool();
    }


}
