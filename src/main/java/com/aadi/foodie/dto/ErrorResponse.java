package com.aadi.foodie.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus status;
}
