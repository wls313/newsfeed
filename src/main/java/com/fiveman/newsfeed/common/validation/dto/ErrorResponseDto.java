package com.fiveman.newsfeed.common.validation.dto;

import java.time.LocalDateTime;

public record ErrorResponseDto(LocalDateTime errorTimestamp,String code,String errorMessage) {
    public ErrorResponseDto(String code,String errorMessage){
        this(LocalDateTime.now(),code,errorMessage);
    }

}
