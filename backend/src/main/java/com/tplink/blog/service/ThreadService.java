package com.tplink.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tplink.blog.dao.mapper.ArticleMapper;
import com.tplink.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xyd
 * @create 2022-12-03 11:36
 */
@Component
public class ThreadService {
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article) {

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getId, article.getId());
        lambdaQueryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        articleMapper.update(articleUpdate, lambdaQueryWrapper);

        System.out.println("=========线程池==============");

        try {
            // 线程睡眠5秒，保证不影响主线程的执行
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 线程池测试
    @Async("taskExecutor")
    public void useThreadPool() {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        // 打印9x9乘法表
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i ; j++) {
                System.out.print(i + "x" + j + "=" + i * j + " ");
            }
            System.out.println();
        }
    }

}
