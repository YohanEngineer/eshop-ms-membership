package com.episen.membership.resource;

import com.episen.membership.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "refresh", produces = {"application/json"})
@RequiredArgsConstructor
public class RefreshResource {

    private final RefreshService refreshService;

    @PostMapping
    public Map<String, String> getNewToken(HttpServletRequest request, HttpServletResponse response) {
       return refreshService.refreshToken(request, response);
    }
}
