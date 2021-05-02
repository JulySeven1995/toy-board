package com.board.api.controller;

import com.board.api.service.PostService;
import com.board.api.service.UserService;
import com.board.common.entity.Post;
import com.board.common.entity.User;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private final User user = new User("jhchoi950707@gmail.com", "최재호", "1234");

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Before
    public void setUpUser() {

        userService.createItem(user);
    }

    @Transactional
    @After
    public void tearDown() {

        userService.deleteItem(user.getUserUid());
    }

    @WithMockUser(value = "spring")
    @Test
    public void 리스트_조회() throws Exception {


        user.addPost(new Post("title", "contents"));
        userService.updateItem(user);

        mvc.perform(get("/api/board/list").contentType(MediaType.APPLICATION_JSON)
                .param("page", NumberUtils.INTEGER_ONE.toString()))
                .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    public void 게시글_세부_조회() throws Exception {

        Post post = new Post(user, "제목", "내용");
        User updatedUser = userService.updateItem(user);

        post = updatedUser.getPosts().stream().findFirst().get();

        mvc.perform(get("/api/board/detail").contentType(MediaType.APPLICATION_JSON)
                .param("postUid", String.valueOf(post.getPostUid())))
                .andExpect(status().isOk());
    }


}