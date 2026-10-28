package com.workintech.twitterapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwitterErrorResponse {
    private int status;
    private String message;
    private long timestamp;
}
