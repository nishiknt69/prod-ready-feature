package com.example.prod_ready_features.services;

import com.example.prod_ready_features.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto postDto);

    PostDto getPostById(Long postId);
}
