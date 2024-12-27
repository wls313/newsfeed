package com.fiveman.newsfeed.user.controller;


import com.fiveman.newsfeed.auth.service.AuthService;
import com.fiveman.newsfeed.user.dto.*;
import com.fiveman.newsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> create(@Valid @RequestBody SignupRequestDto requestDto) {

        UserDto response = userService.createUser(requestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email){
        UserResponseDto userResponseDto = userService.findByEmail(email);

        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> updateUser (@RequestBody UserRequestDto requestDto){

        Long loginId = authService.getLoginUserId();
        UserResponseDto userResponseDto = userService.updateUser(loginId, requestDto.getUsername(), requestDto.getAge());

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<UserPasswordRequestDto> updatePassword (@RequestBody UserPasswordRequestDto requestDto){

        Long loginId = authService.getLoginUserId();
        userService.updatePassword(loginId,requestDto.getOldPassword(),requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<UserResponseDto> deleteUser (@RequestParam String password) {

        Long loginId = authService.getLoginUserId();
        userService.delete(loginId,password);
        authService.logout();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}