package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.common.config.PasswordEncoder;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import com.fiveman.newsfeed.user.dto.UserDto;
import com.fiveman.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDto create(SignupRequestDto request) {

        isDuplicateEmail(request.email());

        User user = userRepository.save(User.of(request, encoder.encode(request.password())));

        return UserDto.from(user);
    }

    private void isDuplicateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
