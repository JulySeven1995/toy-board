package com.board.api.controller;

import com.board.api.dto.UserForm;
import com.board.api.service.ObjectMapperService;
import com.board.api.service.PostFileService;
import com.board.api.service.PostService;
import com.board.api.service.UserService;
import com.board.common.entity.Post;
import com.board.common.entity.PostFile;
import com.board.common.entity.User;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final PostService postService;
    private final PostFileService postFileService;
    private final ObjectMapperService objectMapperService;


    public ViewController(UserService userService, PostService postService, PostFileService postFileService, ObjectMapperService objectMapperService) {
        this.userService = userService;
        this.postService = postService;
        this.postFileService = postFileService;
        this.objectMapperService = objectMapperService;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
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

    @GetMapping("/denied")
    public String denied() {

        return "/error";
    }

    @GetMapping("/boardWrite")
    public String boardWrite() {

        return "/boardWrite";
    }

    @PostMapping("/boardWrite")
    public String execBoardWrite(
            Principal principal,
            @Valid @RequestParam("title") String title,
            @Valid @RequestParam("contents") String contents,
            @Valid @RequestParam("files") List<MultipartFile> files
    ) {

        User user = userService.getUserByEmail(principal.getName()).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));

        postService.publishPost(user, title, contents, files);

        return "redirect:/index";
    }

    @GetMapping("/boardDetail")
    public String boardDetail(@RequestParam("postUid") Long postUid, Model model) {

        Post post = postService.getItemByUid(postUid).orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        model.addAttribute("board", objectMapperService.convertToMap(post));

        return "/boardDetail";
    }

    @GetMapping("/boardModify")
    public String boardModify(@RequestParam("postUid") Long postUid,
                              Principal principal, Model model) {

        Post post = postService.getItemByUid(postUid).orElseThrow(() -> new IllegalArgumentException("Post Not Found"));
        if (!principal.getName().equals(post.getUserEmail())) {

            return "redirect:/denied";
        }
        model.addAttribute("board", objectMapperService.convertToMap(post));

        return "/boardModify";
    }

    @PostMapping("/boardModify")
    public String execBoardModify(
            Principal principal,
            @Valid @RequestParam("postUid") Long postUid,
            @Valid @RequestParam("title") String title,
            @Valid @RequestParam("contents") String contents,
            @RequestParam(required = false, name = "deleteFileList") String[] deleteFileList,
            @Valid @RequestParam("files") List<MultipartFile> files
    ) {


        Post post = postService.getItemByUid(postUid).orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        if (!principal.getName().equals(post.getUserEmail())) {

            return "redirect:/denied";
        }

        if (deleteFileList != null) {

            List<PostFile> postFiles = Arrays.stream(deleteFileList)
                    .map(i -> postFileService.getItemByUid(Long.parseLong(i)).get())
                    .filter(i -> i.getOwner().equals(principal.getName()))
                    .collect(Collectors.toList());

            post.getPostFiles().removeAll(postFiles);
            postFileService.deleteAllByList(postFiles);
        }

        post.setTitle(title);
        post.setContents(contents);
        postService.updatePostWithFiles(post, files);

        return "redirect:/index";
    }


    @GetMapping("/boardDelete")
    public String execBoardDelete(@RequestParam("postUid") Long postUid, Principal principal) {

        Post post = postService.getItemByUid(postUid).orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        if (!principal.getName().equals(post.getUserEmail())) {

            return "redirect:/denied";
        }

        postService.deleteItem(postUid);

        return "redirect:/index";
    }
}
