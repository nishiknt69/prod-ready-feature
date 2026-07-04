package com.example.prod_ready_features.controllers;

import com.example.prod_ready_features.dto.PostDto;
import com.example.prod_ready_features.services.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("{postId}")
    public PostDto getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDto CreateNewPost(@RequestBody PostDto inputPost){
        return postService.createNewPost(inputPost);
    }
}
