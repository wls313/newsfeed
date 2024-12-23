package com.fiveman.newsfeed.user.repository;

import com.fiveman.newsfeed.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    default User findByEmailOrElseThrow(String email) {

        User user = findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist email = " + email));

        // isDelete 가 true 인경우 NOTFOUND 가 출력되게 했습니다
        if (user.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email = " + email + " is deleted.");
        }

        return user;
    }

    boolean existsByEmail(String email);
}
