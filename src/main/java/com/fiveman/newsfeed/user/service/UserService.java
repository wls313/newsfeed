package com.fiveman.newsfeed.user.service;

import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // 예시: 회원가입 처리 메서드
    public String signup(SignupRequestDto signupRequestDto) {
        // 여기에 회원가입 로직 추가
        // 예: 사용자가 입력한 데이터를 기반으로 사용자 생성
        // 예시로 "회원가입 성공" 메시지 반환
        return "회원가입 성공";
    }
}