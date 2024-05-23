package com.springboot.BuidingRealTimeAPI.service;

import com.springboot.BuidingRealTimeAPI.dto.PostDto;
import com.springboot.BuidingRealTimeAPI.dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(long id);

    List<PostDto> getPostsByCategory(Long categoryId);
}
