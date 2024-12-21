package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import com.fiveman.newsfeed.user.dto.UserDto;


public interface UserService {

    UserDto create(SignupRequestDto request);
}
