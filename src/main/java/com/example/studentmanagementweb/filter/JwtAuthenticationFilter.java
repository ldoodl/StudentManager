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
        throws ServletException,  IOException {

        try    {
                System.out.println("🚦 [过滤器] 拦截到请求: " + request.getRequestURI());

                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer")) {
                    String token = authHeader.substring(7);
                    if (jwtUtil.validateToken(token)) {

                        jwtUtil.printTokenInfo(token);

                        String username = jwtUtil.getUsernameFromToken(token);

                        System.out.println("🚦 [过滤器] 请求头中的 Token: " + request.getHeader("Authorization"));

                        UserDetails userDetails = User.withUsername(username).password("").authorities(new ArrayList<>()).build();
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
                chain.doFilter(request, response);
            } catch (io.jsonwebtoken.ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json:charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"登录已过期，请重新登录\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application.json;charst=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Token 无效\"}");
        }
        }


}
