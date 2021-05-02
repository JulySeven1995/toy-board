package com.board.common.repository;

import com.board.common.entity.Post;
import com.board.common.entity.PostFile;
import com.board.common.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostFileRepositoryTest {

    @Autowired
    private PostFileRepository postFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

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
    public void savePostFiles() {

        Post post =  new Post("저장 테스트", "저장 테스트");
        List<PostFile> postFiles = new ArrayList<>();
        postFiles.add(new PostFile("fileName", "originalFileName", "filePath"));
        postFiles.add(new PostFile("fileName", "originalFileName", "filePath"));
        postFiles.add(new PostFile("fileName", "originalFileName", "filePath"));

        post.addPostFiles(postFiles);
        user.addPost(post);
        userRepository.saveAndFlush(user);

        postFiles = postFileRepository.findAll();

        assertTrue(userRepository.findByEmail(user.getEmail()).get().getPosts()
                .stream().findFirst().get().getPostFiles().containsAll(postFiles));
    }

    @Test
    public void updatePostFile() {

        final String fileNameToModify = "수정된이름";
        PostFile postFile = new PostFile("파일이름","원래이름","저장주소");
        postFileRepository.saveAndFlush(postFile);

        PostFile postFileToUpdate = new PostFile();
        postFile.setPostFileUid(postFile.getPostFileUid());
        postFile.setFileName(postFile.getFileName());
        postFile.setOriginalFileName(fileNameToModify);
        postFile.setFilePath(postFile.getFilePath());

        postFileRepository.saveAndFlush(postFileToUpdate);

        assertEquals(postFileRepository.findById(postFile.getPostFileUid()).get().getOriginalFileName(), fileNameToModify);
        postFileRepository.deleteAll();
    }

}
