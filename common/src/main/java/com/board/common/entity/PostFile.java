package com.board.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "POST_FILES")
public class PostFile implements Serializable {

    @Id
    @Column(name = "POST_FILE_UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postFileUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_UID", updatable = false)
    private Post post;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "ORIGIANL_FILE_NAME")
    private String originalFileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    public PostFile() {

    }

    public PostFile(String fileName, String originalFileName, String filePath) {

        this.post = post;
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.filePath = filePath;
    }

    public PostFile(Post post, String filePath) {

        this.post = post;
        this.filePath = filePath;
    }

    public Long getPostFileUid() {

        return postFileUid;
    }

    public void setPostFileUid(Long postFileUid) {

        this.postFileUid = postFileUid;
    }

    public Long getPostUid() {

        return this.post.getPostUid();
    }

    public void setPost(Post post) {

        this.post = post;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {

        this.fileName = fileName;
    }

    public String getOriginalFileName() {

        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {

        this.originalFileName = originalFileName;
    }

    @JsonIgnore
    public String getFilePath() {

        return filePath;
    }

    public void setFilePath(String filePath) {

        this.filePath = filePath;
    }

    public String getOwner() {

        return this.post.getUser().getEmail();
    }

}
