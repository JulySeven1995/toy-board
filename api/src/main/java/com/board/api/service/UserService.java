package com.board.api.service;

import com.board.api.dto.UserForm;
import com.board.common.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService, CrudService<User, Long> {

    User signUpUser(UserForm userForm);

    Optional<User> getUserByEmail(String email);

    void deleteUser(String email);
}