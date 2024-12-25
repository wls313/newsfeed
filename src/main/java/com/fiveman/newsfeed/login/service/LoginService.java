package com.fiveman.newsfeed.login.service;

import com.fiveman.newsfeed.common.config.PasswordEncoder;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.login.dto.LoginRequestDto;
import com.fiveman.newsfeed.user.dto.UserDto;
import com.fiveman.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final UserRepository userRepository;

    public UserDto login(LoginRequestDto loginRequestDto){
        PasswordEncoder encoder = new PasswordEncoder();
        User user = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                if(encoder.matches(loginRequestDto.password(),user.getPassword())){
                    return UserDto.from(user);
                }else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
    }

}
