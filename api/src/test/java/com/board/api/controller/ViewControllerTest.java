package com.board.api.controller;

import com.board.api.service.UserService;
import com.board.common.entity.Post;
import com.board.common.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    private final String email = "julyseven1995@gmail.com";
    private final String password = "1234";
    private final User user = new User(email, "최재호", password);
    private final Post post = new Post(user, "제목", "내용");

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Before
    public void userSetup() {

        userService.createItem(user);
    }

    @After
    public void deleteUser() {

        userService.deleteItem(user.getUserUid());
    }

    @Test
    @WithAnonymousUser
    public void 로그인_페이지_접속() throws Exception {

        mvc.perform(get("/login").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void 회원가입_페이지_접속() throws Exception {

        mvc.perform(get("/signUp").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void 회원가입() throws Exception {

        mvc.perform(post("/signUp").contentType(MediaType.APPLICATION_JSON)
                .param("email", "email")
                .param("userName", "JaehoChoi")
                .param("password","password")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithAnonymousUser
    public void 로그인() throws Exception {

        mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .param("email", email)
                .param("password",password)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "jhchoi950707@gmail.com", authorities = {"GENERAL"})
    public void 인덱스_접속() throws Exception {

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("jhchoi950707@gmail.com");

        mvc.perform(get("/index").contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "jhchoi950707@gmail.com", authorities = {"GENERAL"})
    public void 게시글_작성_페이지() throws Exception {

        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("jhchoi950707@gmail.com");

        mvc.perform(get("/boardWrite").contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal))
                .andExpect(status().isOk());
    }

}