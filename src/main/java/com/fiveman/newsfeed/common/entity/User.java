package com.fiveman.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private short age;

    @ColumnDefault("0")
    @Column(columnDefinition = "boolean", nullable = false)
    private boolean isDeleted;

    public User(){

    }

    public static User of(String username, String email, String password, short age) {
        User user = new User();

        user.username = username;
        user.email = email;
        user.password = password;
        user.age = age;

        return user;
    }


    public void updateUser(String username, short age) {
        this.username = username;
        this.age = age;
    }

    public void updateIsDelete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}