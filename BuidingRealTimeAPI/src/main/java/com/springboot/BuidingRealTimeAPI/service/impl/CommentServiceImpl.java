package com.springboot.BuidingRealTimeAPI.service.impl;

import com.springboot.BuidingRealTimeAPI.dto.CommentDto;
import com.springboot.BuidingRealTimeAPI.entity.Comment;
import com.springboot.BuidingRealTimeAPI.entity.Post;
import com.springboot.BuidingRealTimeAPI.exception.AppAPIException;
import com.springboot.BuidingRealTimeAPI.exception.ResourceNotFoundException;
import com.springboot.BuidingRealTimeAPI.repository.CommentRepository;
import com.springboot.BuidingRealTimeAPI.repository.PostRepository;
import com.springboot.BuidingRealTimeAPI.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        return mapToDTO(comment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment saveComment = commentRepository.save(comment);

        return mapToDTO(saveComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){

         Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
