package com.colak.springoauth2resourceserverjwttutorial.controller;

import com.colak.springoauth2resourceserverjwttutorial.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/token")

@RequiredArgsConstructor
public class TokenController {

    private final JwtService jwtService;

    @PostMapping(path = "/get", consumes = APPLICATION_JSON_VALUE)
    public String getToken(@RequestBody Map<String, Object> claims) {
        return jwtService.generateJWT(claims);
    }

}
