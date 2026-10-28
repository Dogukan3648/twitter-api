package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentDTO(@NotBlank @Size(max = 280) String content, @NotNull Long tweetId) {
}
