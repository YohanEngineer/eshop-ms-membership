package com.episen.membership.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface RefreshService {
    Map<String, String>  refreshToken(HttpServletRequest request, HttpServletResponse response);
}
