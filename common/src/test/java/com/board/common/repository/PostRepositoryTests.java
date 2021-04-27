package com.board.common.repository;

import com.board.common.entity.Post;
import com.board.common.entity.User;
import com.board.common.type.PostType;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private final String USER_ID = "Jaeho";

    private final User user = new User(USER_ID, "멋쟁이 재호", "3145");

    @Transactional
    @Before
    public void setUp() {

        userRepository.saveAndFlush(user);
    }

    @Transactional
    @After
    public void tearDown() {

        userRepository.delete(user);
    }

    @Test
    public void savePosts() {

        Post post =  new Post(user, "저장 테스트");
        post = postRepository.saveAndFlush(post);

        assertEquals(userRepository.findByUserId(USER_ID).get().hashCode(), post.getUser().hashCode());
        assertTrue(postRepository.findAllByUser(userRepository.findByUserId(USER_ID).get()).contains(post));
    }

    @Test
    public void updatePosts() {

        Post post =  new Post(user, "업데이트 테스트");
        post = postRepository.saveAndFlush(post);

        assertEquals(post.getPostType(), PostType.NORMAL);

        post.setPostType(PostType.DELETED);
        postRepository.saveAndFlush(post);

        assertEquals(postRepository.findById(post.getPostUid()).get().getPostType(),
                PostType.DELETED);
    }

    @Test
    public void deletePosts() {

        Post post =  new Post(user, "삭제 테스트");

        post = postRepository.saveAndFlush(post);
        postRepository.delete(post);

        thrown.expect(NoSuchElementException.class);
        postRepository.findById(post.getPostUid()).get();

    }

}
