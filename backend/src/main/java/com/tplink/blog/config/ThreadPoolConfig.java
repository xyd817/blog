package com.tplink.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;



/**
 * @author xyd
 * @create 2022-12-03 11:26
 */
@Configuration
@EnableAsync // 可以异步执行
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        // 线程池实例
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        // 设置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间(秒)
        executor.setKeepAliveSeconds(60);
        // 设置线程默认名称
        executor.setThreadNamePrefix("线程池执行-");
        // 等待所有任务结束后关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 执行初始化
        executor.initialize();
        return executor;
    }



}
