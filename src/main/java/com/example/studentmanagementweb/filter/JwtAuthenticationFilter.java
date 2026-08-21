package com.example.studentmanagementweb.filter;

import com.example.studentmanagementweb.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("🔍 [过滤器] 拦截到请求: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        System.out.println("🔍 [过滤器] Authorization 头: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("🔍 [过滤器] 提取的 Token: " + token);

            try {
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.getUsernameFromToken(token);
                    System.out.println("🔍 [过滤器] ✅ 用户名: " + username);

                    UserDetails userDetails = User.withUsername(username)
                            .password("")
                            .authorities(new ArrayList<>())
                            .build();
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("🔍 [过滤器] ✅ 认证已设置到 SecurityContext");
                } else {
                    System.out.println("🔍 [过滤器] ❌ Token 验证失败");
                }
            } catch (Exception e) {
                System.out.println("🔍 [过滤器] ❌ Token 解析异常: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("🔍 [过滤器] ⚠️ 未携带 Token，跳过认证");
        }

        chain.doFilter(request, response);
    }


}
