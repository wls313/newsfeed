package com.fiveman.newsfeed.user.controller;

import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import com.fiveman.newsfeed.user.dto.UserDto;
import com.fiveman.newsfeed.user.service.UserService;
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
    public ResponseEntity<UserDto> create(@RequestBody SignupRequestDto requestDto) {

        UserDto response = userService.create(requestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/email")
    public ResponseEntity<UserDto> findByEmail(@RequestParam String email) {

        UserDto response = userService.findByEmail(email);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber
    ) {
        Pageable pageable = PageRequest.of(pageNumber-1,pageSize);
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }
}
