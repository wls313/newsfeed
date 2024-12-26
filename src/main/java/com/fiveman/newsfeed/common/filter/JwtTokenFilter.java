package com.fiveman.newsfeed.common.filter;

import com.fiveman.newsfeed.common.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider; // JWT 처리 클래스

    // JWT 토큰을 이용한 인증 과정
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");    // 헤더 추출

        if (authHeader != null && authHeader.startsWith("Bearer ")) {   // 헤더 부분에서 토큰 추출
            String token = authHeader.substring(7);

            if (jwtTokenProvider.validateToken(token)) {         // 토큰이 만료됐는지 유효한 서명인지 검사
                String email = jwtTokenProvider.getEmail(token);
                Long userId = jwtTokenProvider.getUserId(token); // userId 추출

                UserDetails userDetails = User.withUsername(email)
                        .password("") // 실제 비밀번호는 필요하지 않음
                        .authorities("ROLE_USER") // 기본 권한
                        .build();

                UsernamePasswordAuthenticationToken authentication =    // 인증 정보를 담는 객체
                        new UsernamePasswordAuthenticationToken(userId, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext(쓰레드 메모리에 존재) 에 인증 객체를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);
    }
}
