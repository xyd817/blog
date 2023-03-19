package com.tplink.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tplink.blog.dao.dos.Archives;
import com.tplink.blog.dao.mapper.ArticleBodyMapper;
import com.tplink.blog.dao.mapper.ArticleMapper;
import com.tplink.blog.dao.mapper.ArticleTagMapper;
import com.tplink.blog.dao.pojo.Article;
import com.tplink.blog.dao.pojo.ArticleBody;
import com.tplink.blog.dao.pojo.ArticleTag;
import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.service.*;
import com.tplink.blog.utils.UserThreadLocal;
import com.tplink.blog.vo.*;
import com.tplink.blog.vo.params.ArticleParam;
import com.tplink.blog.vo.params.ArticleUpdateParam;
import com.tplink.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyd
 * @create 2022-10-12 15:57
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired(required = false)
    private ArticleMapper articleMapper;
    @Autowired
    public TagsService tagsService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired(required = false)
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private ThreadService threadService;

    @Autowired(required = false)
    private ArticleTagMapper articleTagMapper;

    /**
     * 文章列表
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {

        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        Long categoryId = null;
        if (pageParams.getCategoryId() != null) {
            categoryId = Long.parseLong(pageParams.getCategoryId());
        }
        Long tagId = null;
        if (pageParams.getTagId() != null) {
            tagId = Long.parseLong(pageParams.getTagId());
        }
        String year = null;
        if (pageParams.getYear() != null) {
            year = pageParams.getYear();
        }
        String month = null;
        if (pageParams.getMonth() != null) {
            month = pageParams.getMonth();
        }
        IPage<Article> articleIPage = articleMapper.ListArticles(page, categoryId,
                tagId, year,month);
        return Result.success(copyList(articleIPage.getRecords(), true, true));

    }
//    public Result listArticle(PageParams pageParams) {
//        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
//
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//
//        ArrayList<Long> articleIds = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIds.add(articleTag.getArticleId());
//            }
//
//            if (articleIds.size() > 0 ) {
//                queryWrapper.in(Article::getId, articleIds);
//            }
//        }

//        // 进行排序
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//
//        List<Article> records = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(records,true,true);
//
//        return Result.success(articleVoList);
//    }

    /**
     * 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));
    }

    /**
     * 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate)
                .select(Article::getId, Article::getTitle)
                .last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archives> archives = articleMapper.listArchives();
        return Result.success(archives);
    }

    /**
     * 通过文章id获取文章信息
     * @param id
     * @return
     */
    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        // 使用线程池更新阅读次数
        threadService.updateViewCount(articleMapper, article);
        return copy(article, true,true,true,true);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {

        // 插入文章表
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setWeight(Article.Article_TOP);
        article.setBodyId(-1L);


        articleMapper.insert(article);

        // 插入文章与文章标签关联表
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(Long.parseLong(tag.getId()));
                articleTagMapper.insert(articleTag);
            }
        }
        // 插入文章内容表
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        // 更新文章内容id
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        return Result.success(articleVo);
    }

    @Override
    public Result updateArticle(ArticleUpdateParam articleUpdateParam) {
        LambdaUpdateWrapper<ArticleBody> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(ArticleBody::getArticleId, articleUpdateParam.getArticleId())
                .set(ArticleBody::getContent, articleUpdateParam.getContent());
        articleBodyMapper.update(null, lambdaUpdateWrapper);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(articleUpdateParam.getArticleId()));
        return Result.success(articleVo);
    }


    /**
     * 将Article列表转换为ArticleVo列表
     * @param records
     * @param isAuthor
     * @param isTag
     * @return
     */
    private List<ArticleVo> copyList(List<Article> records,boolean isAuthor, boolean isTag) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,isTag, false, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag, boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,isTag, isBody, false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTag,
                                     boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isAuthor,isTag, isBody, isCategory));
        }
        return articleVoList;
    }

    /**
     * 将Article对象转为ArticleVo对象
     * @param article
     * @param isAuthor
     * @param isTag
     * @param isBody
     * @param isCategory
     * @return
     */
    private ArticleVo copy(Article article, boolean isAuthor, boolean isTag, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        if(isAuthor) {
            SysUser user = sysUserService.findUserById(article.getAuthorId());
            articleVo.setAuthor(user.getNickname());
        }
        if(isTag) {
            articleVo.setTags(tagsService.findTagsByArticleId(article.getId()));
        }

        if (isBody) {
            ArticleBodyVo articleBody = findArticleBody(article.getId());
            articleVo.setBody(articleBody);
        }

        if (isCategory) {
            CategoryVo category = findCategory(article.getCategoryId());
            articleVo.setCategory(category);
        }
        articleVo.setId(article.getId().toString());
        return articleVo;
    }

    /**
     * 依据类别id获取类别类别
     * @param categoryId
     * @return
     */
    private CategoryVo findCategory(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    /**
     * 依据文章id获取文章详情
     * @param articleId
     * @return
     */
    @Override
    public ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleBody::getArticleId, articleId);
        ArticleBody articleBody = articleBodyMapper.selectOne(lambdaQueryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }


}
