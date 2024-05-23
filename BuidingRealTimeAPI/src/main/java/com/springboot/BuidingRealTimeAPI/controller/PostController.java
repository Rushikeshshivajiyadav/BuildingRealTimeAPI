package com.springboot.BuidingRealTimeAPI.controller;

import com.springboot.BuidingRealTimeAPI.dto.PostDto;
import com.springboot.BuidingRealTimeAPI.dto.PostDto2;
import com.springboot.BuidingRealTimeAPI.dto.PostResponse;
import com.springboot.BuidingRealTimeAPI.service.PostService;
import com.springboot.BuidingRealTimeAPI.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }



    @Operation(
            summary = "Create Post Rest API",
            description = "create post Rest Api is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 created"
    )
    // create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto>  createPost(@Valid @RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }




    @Operation(
            summary = "Get ALL Post Rest API",
            description = "Get ALL Post By Id Rest Api is used to get all post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(@RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
                                    ){

        return postService.getAllPosts(pageNo,pageSize, sortBy,sortDir);
    }




    @Operation(
            summary = "Get Post By Id Rest API",
            description = "Get Post By Id Rest Api is used to get a single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @GetMapping("/api/v1/posts/{id}")                   //  /api/posts/{id}     params = "version1"
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id){

//         return ResponseEntity.ok(postService.getPostById(id));
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }


    @GetMapping("/api/v2/posts/{id}")              //  /api/posts/{id}                         - path versioning  - google, paypal,twiter
                                                    // params = "version2"                       - Query versioning  -
                                                     //  headers = "X-API-VERSION=1"             - custom header
                                                    //  produces = "application/vnd.buildrestapi.v1+json  - content negotiation  - github
    public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable long id){

        PostDto postDto = postService.getPostById(id);

        PostDto2 postDto2 = new PostDto2();
        postDto2.setId(postDto.getId());
        postDto2.setTitle(postDto.getTitle());
        postDto2.setDescription(postDto.getDescription());
        postDto2.setContent(postDto.getContent());
            List<String> tags = new ArrayList<>();
            tags.add("Java");
            tags.add("Spring Boot");
            tags.add("AWS");
            postDto2.setTags(tags);
//         return ResponseEntity.ok(postService.getPostById(id));
        return new ResponseEntity<>(postDto2, HttpStatus.OK);
    }



    @Operation(
            summary = "update Post By Id Rest API",
            description = "update Post By Id Rest Api is used to update a particular post into the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }



    @Operation(
            summary = "delete Post By Id Rest API",
            description = "delete Post By Id Rest Api is used to delete a single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletepost(@PathVariable long id){

        postService.deletePost(id);
        return new ResponseEntity<>("delete post suceessfully",HttpStatus.OK);
    }

    // Build get Posts by category Rest API
    @GetMapping("/api/v1/posts/category/{categoryId}")
    public ResponseEntity<List<PostDto>>  getPostsByCategory(@PathVariable Long categoryId){
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
    }
}
