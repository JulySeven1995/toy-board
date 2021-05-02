package com.board.api.initialize;

import com.board.api.service.UserService;
import com.board.common.entity.Post;
import com.board.common.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;


@Profile("local")
@Component
public class DefaultDataInitializer {

    private final UserService userService;

    public DefaultDataInitializer(UserService userService) {

        this.userService = userService;
    }

    @Transactional
    @PostConstruct
    public void initTestData() {

        User user = new User("jhchoi950707@gmail.com", "최재호", "3145");

        user.addPost(new Post("안녕하세요 최재호입니다.", "저는 1995년 7월 7월생입니다."));
        user.addPost(new Post("기재된 요구사항은 전부 충족시켰습니다.", "오랜만에 프론트와 함께 개발하니 쉽지 않았습니다."));
        user.addPost(new Post("Controller의 Method들이 Restful 하지는 않습니다.", "많이 구현도 되지 않았습니다."));

        user.addPost(new Post("만약 대량의 게시글 테스트가 필요하다면", "DefaultDataInitializer의 createTestData 메서드를 실행시켜주세요."));

        //this.createTestData(user);
        userService.createItem(user);
    }

    private void createTestData(User user) {

        for (int i = 1; i < 324; i++) {

            user.addPost(new Post(user, "테스트 게시글 " + i, "테스트 게시글 내용 " + i));
        }
    }

}
