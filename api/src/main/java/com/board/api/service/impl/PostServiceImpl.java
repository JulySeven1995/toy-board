package com.board.api.service.impl;

import com.board.api.service.PostService;
import com.board.common.entity.Post;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public Optional<Post> getItemByUid(Long uid) {
        return Optional.empty();
    }

    @Override
    public Post createItem(Post user) {
        return null;
    }

    @Override
    public Post updateItem(Post user) {
        return null;
    }

    @Override
    public void deleteItem(Long uid) {

    }
}
