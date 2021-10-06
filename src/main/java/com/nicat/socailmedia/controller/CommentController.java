package com.nicat.socailmedia.controller;

import com.nicat.socailmedia.dto.request.CreateCommentRequest;
import com.nicat.socailmedia.dto.response.ApiMessage;
import com.nicat.socailmedia.dto.response.CommentResponse;
import com.nicat.socailmedia.mapper.CommentMapper;
import com.nicat.socailmedia.service.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController implements ApiBuilder {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<ApiMessage> create(
            @Valid @RequestBody CreateCommentRequest request){
        log.info("Service for creation account accepted");
        commentService.create(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }
    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(){
        List<CommentResponse> commentResponses =commentService.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(commentResponses);
    }
}
