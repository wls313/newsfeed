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

        validatePassword(request.password());

        User user = userRepository.save(User.of(request.username(), request.email(), encoder.encode(request.password()), request.age()));

        return UserDto.from(user);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmailOrElseThrow(email);

        return new UserResponseDto(user.getUsername(),user.getEmail());
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserDto::from);
    }

    private void isDuplicateEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @Transactional
    public void updateUser(String email, String username, short age) {

        User user = userRepository.findByEmailOrElseThrow(email);

        user.updateUser(username,age);
    }

    @Transactional
    public void updatePassword(String email, String oldPassword, String newPassword) {

        User user = userRepository.findByEmailOrElseThrow(email);

        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        if (oldPassword.equals(newPassword)){
            throw new IllegalArgumentException("새로운 비밀번호와 원래 비밀번호가 동일합니다.");
        }

        validatePassword(newPassword);

        String password = encoder.encode(newPassword);

        user.updatePassword(password);
        userRepository.save(user);
    }

    private void validatePassword(String newPassword) {
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나 이상의 대문자를 포함해야 합니다.");
        }
        if (!newPassword.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나 이상의 소문자를 포함해야 합니다.");
        }
        if (!newPassword.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나 이상의 숫자를 포함해야 합니다.");
        }
        if (!newPassword.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 하나 이상의 특수 문자를 포함해야 합니다.");
        }
    }

    public void delete(String email) {

        User user = userRepository.findByEmailOrElseThrow(email);

        user.updateIsDelete(true);
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}