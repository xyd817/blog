package com.tplink.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tplink.blog.dao.mapper.CommentMapper;
import com.tplink.blog.dao.pojo.Comment;
import com.tplink.blog.dao.pojo.SysUser;
import com.tplink.blog.service.CommentsService;
import com.tplink.blog.service.SysUserService;
import com.tplink.blog.utils.UserThreadLocal;
import com.tplink.blog.vo.CommentVo;
import com.tplink.blog.vo.Result;
import com.tplink.blog.vo.UserVo;
import com.tplink.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyd
 * @create 2022-12-03 18:05
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired(required = false)
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 通过文章id获取文章的评论
     * @param id
     * @return
     */
    @Override
    public Result commentsByArticleId(Long id) {
        /**
         * 1、通过文章id获取所有一级评论
         * 2、通过评论对象中的评论作者id获取用户信息
         * 3、获取每一条子评论
         */
        // 获取文章的评论信息
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId, id)
                .eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        return Result.success(copyList(comments));
    }

    /**
     * 新建评论
     * @param commentParam
     * @return
     */
    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(Long.parseLong(commentParam.getArticleId()));
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUser.getId());
//        comment.setAuthorId(1L);
        comment.setCreateDate(System.currentTimeMillis());

        Long parent = commentParam.getParent() == null ? null : Long.parseLong(commentParam.getParent());
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(commentParam.getParent() == null ? 0 : parent);

        Long toUserId = commentParam.getToUserId() == null ? null : Long.parseLong(commentParam.getToUserId());
        comment.setToUid( toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);
        return Result.success(copy(comment));
    }


    /**
     * 将Comment对象列表转为CommentVo列表
     * @param comments
     * @return
     */
    private List<CommentVo> copyList(List<Comment> comments) {
        ArrayList<CommentVo> commentVos = new ArrayList<>();
        for (Comment comment : comments) {
            commentVos.add(copy(comment));
        }
        return commentVos;
    }

    /**
     * 将Comment对象转换为CommentVo
     * @param comment
     * @return
     */
    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        // 格式化时间
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 设置评论用户信息
        UserVo userVo = sysUserService.findUserVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);
        // 获取子评论
        List<CommentVo> childComments = findCommentsByParentId(comment.getId());
        commentVo.setChildrens(childComments);
        if (comment.getLevel() > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        commentVo.setId(comment.getId().toString());
        return commentVo;
    }

    /**
     * 获取评论的所有子评论
     * @param commentId
     * @return
     */
    private List<CommentVo> findCommentsByParentId(Long commentId) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getParentId, commentId)
                .eq(Comment::getLevel, 2);
        List<Comment> comments = commentMapper.selectList(lambdaQueryWrapper);
        return copyList(comments);
    }


}
