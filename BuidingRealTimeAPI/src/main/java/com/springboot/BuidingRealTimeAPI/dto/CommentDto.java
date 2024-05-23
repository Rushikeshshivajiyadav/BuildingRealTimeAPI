package com.springboot.BuidingRealTimeAPI.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private long id;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Pattern(regexp = "[a-zA-Z]{1,}[0-9]*[@]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,4}")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;
}
