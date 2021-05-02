package com.board.api.service;

import com.board.common.entity.PostFile;

import java.util.List;

public interface PostFileService extends CrudService<PostFile, Long> {

    void deleteAllByList(List<PostFile> postFiles);
}
