package com.board.api.service;


import com.board.common.entity.Post;
import com.board.common.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService extends CrudService<Post, Long> {

    List<Post> getPostsPaging(Integer page);

    Long getAllPostsCount();

    Post publishPost(User user, String title, String contents, List<MultipartFile> files);

    Post updatePostWithFiles(Post post, List<MultipartFile> files);
}
