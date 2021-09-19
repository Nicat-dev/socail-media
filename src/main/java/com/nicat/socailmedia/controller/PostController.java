package com.nicat.socailmedia.controller;

import com.nicat.socailmedia.dto.request.CreateAccountRequest;
import com.nicat.socailmedia.dto.request.CreatePostRequest;
import com.nicat.socailmedia.dto.response.AccountResponse;
import com.nicat.socailmedia.dto.response.ApiMessage;
import com.nicat.socailmedia.dto.response.PostResponse;
import com.nicat.socailmedia.mapper.PostMapper;
import com.nicat.socailmedia.service.PostService;
import com.nicat.socailmedia.util.ApiBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController implements ApiBuilder {
    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping
    public ResponseEntity<ApiMessage> create(@Valid @RequestBody CreatePostRequest request){
        log.info("Service for creation account accepted");
        postService.create(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll(){
        List<PostResponse> postResponses =postService.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(postResponses);
    }
}
