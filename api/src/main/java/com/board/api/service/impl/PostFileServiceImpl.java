package com.board.api.service.impl;

import com.board.api.service.PostFileService;
import com.board.common.entity.PostFile;
import com.board.common.repository.PostFileRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostFileServiceImpl implements PostFileService {

    private final PostFileRepository repository;

    public PostFileServiceImpl(PostFileRepository repository) {

        this.repository = repository;
    }

    @Override
    public Optional<PostFile> getItemByUid(Long uid) {

        return repository.findById(uid);
    }

    @Override
    public PostFile createItem(PostFile item) {

        return repository.saveAndFlush(item);
    }

    @Override
    public PostFile updateItem(PostFile item) {

        return repository.saveAndFlush(item);
    }

    @Transactional
    @Override
    public void deleteItem(Long uid) {

        repository.deleteById(uid);
    }

    @Transactional
    @Override
    public void deleteAllByList(List<PostFile> postFiles) {

        postFiles.forEach(i -> this.deleteItem(i.getPostFileUid()));
    }
}
