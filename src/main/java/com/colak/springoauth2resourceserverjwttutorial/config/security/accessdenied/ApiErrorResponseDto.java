package com.colak.springoauth2resourceserverjwttutorial.config.security.accessdenied;

public record ApiErrorResponseDto(
        int errorCode,
        String description) {

}
