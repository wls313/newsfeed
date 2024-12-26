package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.common.config.PasswordEncoder;
import com.fiveman.newsfeed.common.entity.User;
import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import com.fiveman.newsfeed.user.dto.UserDto;
import com.fiveman.newsfeed.user.dto.UserResponseDto;
import com.fiveman.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserDto createUser(SignupRequestDto request) {
        isDuplicateEmail(request.email());

        User user = userRepository.save(User.of(request.username(), request.email(), encoder.encode(request.password()), request.age()));

        return UserDto.from(user);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, email+"를 이메일로 가진 유저가 없습니다."));

        return new UserResponseDto(user.getUsername(),user.getEmail());
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAllUsers(pageable)
                .map(UserDto::from);
    }

    private void isDuplicateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,email+"은 이미 가입된 이메일입니다.");
        }
    }

    @Transactional
    public UserResponseDto updateUser(Long id,String username, Short age) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."));


        if(username != null && !username.isEmpty()){
            user.updateUsername(username);
        }

        if (age != null) {
            user.updateAge(age);
        }

        return new UserResponseDto(user.getUsername(),user.getEmail());
    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."));

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        if (oldPassword.equals(newPassword)){
            throw new IllegalArgumentException("새로운 비밀번호와 원래 비밀번호가 동일합니다.");
        }

        String password = encoder.encode(newPassword);

        user.updatePassword(password);
        userRepository.save(user);
    }

    public void delete(Long id,String password) {



        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."));

        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        user.updateIsDelete(true);
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 유저가 없습니다."));
    }

    public User findLoginUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,email+"를 이메일로 가진 유저가 없습니다."));
    }
}