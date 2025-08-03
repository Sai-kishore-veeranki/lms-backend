package com.vsk.lms.user_service.client;

import com.vsk.lms.user_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth-service", url = "${auth.service.url}", configuration = FeignConfig.class)
public interface AuthClient {
    @GetMapping("/api/auth/validate")
    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);
}


