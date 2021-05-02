package com.board.api.service.impl;

import com.board.api.service.PostService;
import com.board.common.entity.Post;
import com.board.common.entity.PostFile;
import com.board.common.entity.User;
import com.board.common.repository.PostRepository;
import com.board.api.utils.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostRepository repository;
    private final FileUtils fileUtils;

    public PostServiceImpl(PostRepository repository, FileUtils fileUtils) {
        this.repository = repository;
        this.fileUtils = fileUtils;
    }

    @Override
    public Optional<Post> getItemByUid(Long uid) {

        return repository.findById(uid);
    }

    @Override
    public Post createItem(Post post) {

        if (post.getPostUid() != null && this.getItemByUid(post.getPostUid()).isPresent()) {

            throw new HibernateException(String.format("Already Exist Post Item POST_UID : [%s]", post.getPostUid()));
        }
        return repository.saveAndFlush(post);
    }

    @Transactional
    @Override
    public Post updateItem(Post post) {

        if (post.getPostUid() == null || !this.getItemByUid(post.getPostUid()).isPresent()) {

            throw new HibernateException(String.format("Not Exist Post Item POST_UID : [%s]", post.getPostUid()));
        }

        return repository.saveAndFlush(post);
    }

    @Transactional
    @Override
    public void deleteItem(Long uid) {

        if (uid == null || !this.getItemByUid(uid).isPresent()) {

            throw new HibernateException(String.format("Not Exist Post Item POST_UID : [%s]", uid));
        }

        repository.deleteById(uid);
    }

    @Override
    public List<Post> getPostsPaging(Integer page) {

        if (page < NumberUtils.INTEGER_ONE) {

            throw new IllegalArgumentException("Page Cannot Be Less Than 0");
        }

        int start = page - 1;
        int size = 10;

        return repository.findAll(PageRequest.of(start, size, Sort.by("postUid").descending())).getContent();
    }

    @Override
    public Long getAllPostsCount() {

        return repository.count();
    }

    @Transactional
    @Override
    public Post publishPost(User user, String title, String contents, List<MultipartFile> files) {

        Post post = new Post(user, title, contents);

        if (files.get(NumberUtils.INTEGER_ZERO).getSize() > NumberUtils.INTEGER_ZERO) {
            this.postFileUpload(post, files);
        }
        return this.createItem(post);
    }

    @Override
    public Post updatePostWithFiles(Post post, List<MultipartFile> files) {

        if (files.get(NumberUtils.INTEGER_ZERO).getSize() > NumberUtils.INTEGER_ZERO) {
            this.postFileUpload(post, files);
        }
        return updateItem(post);
    }

    private void postFileUpload(Post post, List<MultipartFile> files) {

        post.addPostFiles(files.stream().map(i -> {
            PostFile postFile = new PostFile();
            postFile.setFileName(i.getName());
            postFile.setOriginalFileName(i.getOriginalFilename());
            try {
                Path filePath = fileUtils.createPath(String.valueOf(post.getUser().getUserUid()), i.getOriginalFilename());
                i.transferTo(filePath);
                postFile.setFilePath(filePath.toAbsolutePath().toString());
            } catch (IOException e) {
                logger.error("Post File Save System Exception ->", e);
                return null;
            }
            return postFile;
        }).filter(Objects::nonNull).collect(Collectors.toList()));
    }

}
