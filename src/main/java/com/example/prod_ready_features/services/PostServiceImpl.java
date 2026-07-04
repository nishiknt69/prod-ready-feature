package com.example.prod_ready_features.services;

import com.example.prod_ready_features.dto.PostDto;
import com.example.prod_ready_features.entity.PostEntity;
import com.example.prod_ready_features.exceptions.ResourceNotFoundException;
import com.example.prod_ready_features.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto postDto) {
        PostEntity postEntity = modelMapper.map(postDto, PostEntity.class);

        return modelMapper.map(postRepository.save(postEntity), PostDto.class);

    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));

        return modelMapper.map(postEntity, PostDto.class);
    }
}
