package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotNull;

public record LikeDTO(@NotNull Long tweetId) {
}
