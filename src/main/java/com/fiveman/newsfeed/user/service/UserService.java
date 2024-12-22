package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import com.fiveman.newsfeed.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserDto create(SignupRequestDto request);

    UserDto findByEmail(String email);

    Page<UserDto> findAll(Pageable pageable);

    User findById(Long id);

}
