package com.springboot.BuidingRealTimeAPI.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {

    private long id;


    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post Title should have at least 2 characters")
    private String title;


    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post Title should have at least 10 characters")
    private  String description;


    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    private  String content;

    private Set<CommentDto> comment;


    @Schema(
            description = "Blog Post Category"
    )
    private Long categoryId;
}
