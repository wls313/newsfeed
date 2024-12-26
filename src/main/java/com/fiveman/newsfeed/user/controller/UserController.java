package com.fiveman.newsfeed.user.controller;


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

    @PatchMapping("/{email}")
    public ResponseEntity<UserResponseDto> updateUser (@PathVariable String email, @RequestBody UserRequestDto requestDto){
        userService.updateUser(email,requestDto.getUsername(),requestDto.getAge());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("password/{email}")
    public ResponseEntity<UserPasswordRequestDto> updatePassword (@PathVariable String email, @RequestBody UserPasswordRequestDto requestDto){
        userService.updatePassword(email,requestDto.getOldPassword(),requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<UserResponseDto> delete(@PathVariable String email) {
        userService.delete(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}