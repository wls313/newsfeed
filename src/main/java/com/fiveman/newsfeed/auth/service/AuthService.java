package com.fiveman.newsfeed.auth.service;

import com.fiveman.newsfeed.common.config.PasswordEncoder;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.common.security.JwtTokenProvider;
import com.fiveman.newsfeed.auth.dto.LoginRequestDto;
import com.fiveman.newsfeed.auth.dto.LoginResponseDto;
import com.fiveman.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequest) {

        User user = userService.findLoginUserByEmail(loginRequest.getEmail());

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

            Long userId = user.getUserId(); // DB에서 가져온 userId
            String token = jwtTokenProvider.createToken(loginRequest.getEmail(), userId);
            return new LoginResponseDto(token);

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public Long getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Long)
        {
            return (Long) authentication.getPrincipal();
        }
        throw new IllegalStateException("로그인된 사용자가 없습니다.");
    }

    public String getLoginUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Long)
        {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new IllegalStateException("로그인된 사용자가 없습니다.");
    }

}