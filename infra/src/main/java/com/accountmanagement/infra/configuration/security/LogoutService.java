package com.accountmanagement.infra.configuration.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final RedisTemplate<Object, Object> redisTemplate;
  private final JwtService jwtService;

  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    String jwt = authHeader.substring(7);
    String userEmail = jwtService.extractUsername(jwt);
    var storedToken = redisTemplate.opsForValue().get(userEmail);
    if (storedToken != null) {
      redisTemplate.delete(userEmail);
      SecurityContextHolder.clearContext();
    }
  }
}
