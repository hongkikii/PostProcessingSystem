package com.study.communityService.post.controller;

import com.study.communityService.post.controller.port.PostService;
import com.study.communityService.post.controller.response.PostResponse;
import com.study.communityService.post.domain.ContentUpdate;
import com.study.communityService.post.domain.Headerupdate;
import com.study.communityService.post.domain.PostCreate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/latest")
    public ResponseEntity<List<PostResponse>> getByLatest(@RequestParam("startPage") int startPage) {
        return ResponseEntity
                .ok()
                .body(postService.getLatest(startPage).stream()
                        .map(PostResponse::from)
                        .toList());
    }

    @GetMapping("/views")
    public ResponseEntity<List<PostResponse>> getByViews(@RequestParam("startPage") int startPage) {
        return ResponseEntity
                .ok()
                .body(postService.getByViews(startPage).stream()
                        .map(PostResponse::from)
                        .toList());
    }

    @GetMapping("/likes")
    public ResponseEntity<List<PostResponse>> getByLikes(@RequestParam("startPage") int startPage) {
        return ResponseEntity
                .ok()
                .body(postService.getByLikes(startPage).stream()
                        .map(PostResponse::from)
                        .toList());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> getByKeyword(@RequestParam("startPage") int startPage, @RequestParam("keyword") String keyword) {
        return ResponseEntity
                .ok()
                .body(postService.getByKeywords(startPage, keyword).stream()
                        .map(PostResponse::from)
                        .toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity
                .ok()
                .body(PostResponse.from(postService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody PostCreate postCreate) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.create(postCreate)));
    }

    @PutMapping("/header/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable("id") long id, @RequestBody Headerupdate headerupdate) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.update(id, headerupdate)));
    }

    @PutMapping("/content/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable("id") long id, @RequestBody ContentUpdate contentUpdate) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PostResponse.from(postService.update(id, contentUpdate)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id) {
        postService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("게시물을 성공적으로 삭제하였습니다.");
    }
}
