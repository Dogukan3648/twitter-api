package com.workintech.twitterapi.dto;

import jakarta.validation.constraints.NotNull;

public record RetweetDTO(@NotNull Long tweetId) {
}
