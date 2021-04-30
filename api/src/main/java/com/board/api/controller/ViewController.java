package com.board.api.controller;
import com.board.api.dto.UserForm;
import com.board.api.service.UserService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserController userController;

    private final UserService userService;

    public ViewController(UserController userController, UserService userService) {
        this.userController = userController;
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index() {

        return "/index";
    }

    @GetMapping("/signUp")
    public String signUp() {

        return "/signUp";
    }

    @PostMapping("/signUp")
    public String execSignup(UserForm userForm) {

        String result = "redirect:/login";

        try {
            userService.signUpUser(userForm);
        } catch (HibernateException e) {
            logger.error("Create User Failed -> ", e);
            result = "redirect:/signUp?error";
        }

        return result;
    }

    @GetMapping("/login")
    public String doLogin() {

        return "/login";
    }

    // 로그인 결과 페이지
    @GetMapping("/login/result")
    public String loginResult() {

        return "redirect:/index";
    }

    // 로그아웃 결과 페이지
    @GetMapping("/logout/result")
    public String logout() {

        return "/logout";
    }

    // 접근 거부 페이지
    @GetMapping("/denied")
    public String denied() {

        return "/denied";
    }
}
