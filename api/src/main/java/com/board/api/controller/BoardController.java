package com.board.api.controller;

import com.board.api.service.ObjectMapperService;
import com.board.api.service.PostService;
import com.board.common.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/board")
@ResponseBody
public class BoardController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostService postService;

    private final ObjectMapperService objectMapperService;

    public BoardController(PostService postService, ObjectMapperService objectMapperService) {

        this.postService = postService;
        this.objectMapperService = objectMapperService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list", params = "page")
    public Map<String, Object> boardList(@RequestParam Integer page) {

        Map<String, Object> result = new HashMap<>();
        List<Post> posts = postService.getPostsPaging(page);

        result.put("count", postService.getAllPostsCount());
        result.put("list", posts.stream().map(objectMapperService::convertToMap).collect(Collectors.toList()));
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/detail", params = "postUid")
    public Map<String, Object> boardDetail(@RequestParam Long postUid) {

        Post post = postService.getItemByUid(postUid)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Post Do Not Exists [%s]", postUid)));

        return objectMapperService.convertToMap(post);
    }
}
