package com.fiveman.newsfeed.common.entity;

import com.fiveman.newsfeed.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long userId;

    @Column(unique=true,nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private Short age;

    @Column(columnDefinition = "BOOLEAN default false")
    private boolean isDeleted;

    public static User of(SignupRequestDto request, String encodePassword) {
        User user = new User();
        user.email = request.email();
        user.password = encodePassword;
        user.age = request.age();
        return user;
    }

}
