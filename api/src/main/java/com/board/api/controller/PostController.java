package com.board.api.controller;

import com.board.api.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }


    @PostMapping("/board/list")
    public List<Map<String, Object>> boardList() {

        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();

        return null;
    }
}
