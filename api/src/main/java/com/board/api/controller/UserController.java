package com.board.api.controller;

import com.board.api.dto.UserForm;
import com.board.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;


@Controller
@ResponseBody
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/signUp")
    public void signUpUser(@RequestBody @Valid UserForm userForm) throws Exception {

        userService.signUpUser(userForm);
    }
}
