package com.board.api.controller;

import com.board.api.service.ObjectMapperService;
import com.board.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Controller
@RequestMapping("/api/user")
@ResponseBody
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final ObjectMapperService objectMapperService;

    public UserController(UserService userService, ObjectMapperService objectMapperService) {
        this.userService = userService;
        this.objectMapperService = objectMapperService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getUserInfo", params = "email")
    public Map<String, Object> getUserInfo(@RequestParam String email) {

        logger.info("[GET] User Information Request, Email = [{}]", email);
        return userService.getUserByEmail(email)
                .map(objectMapperService::convertToMap)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

}
