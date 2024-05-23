package com.springboot.BuidingRealTimeAPI.service;

import com.springboot.BuidingRealTimeAPI.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, long commentId, CommentDto commentDto);

    public void deleteComment(Long postId, Long commentId);
}
