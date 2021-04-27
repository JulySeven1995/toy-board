package com.board.api.controller;

import com.board.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }
}
