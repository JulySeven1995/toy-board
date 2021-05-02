package com.board.common.entity;

import com.board.common.converter.PostTypeConverter;
import com.board.common.type.PostType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "POST")
public class Post implements Serializable {

    @Id
    @Column(name = "POST_UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postUid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_UID")
    private User user;

    @Column(name = "POST_TYPE")
    @Convert(converter = PostTypeConverter.class)
    private PostType postType = PostType.NORMAL;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @UpdateTimestamp
    @Column(name = "MODIFED_DATE")
    private Date modifiedDate;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENTS")
    private String contents;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<PostFile> postFiles = new ArrayList<>();

    public Post() {

    }

    public Post(User user, String title, String contents) {
        this.user = user;
        this.user.addPost(this);
        this.title = title;
        this.contents = contents;
    }

    public Post(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Long getPostUid() {

        return postUid;
    }

    public void setPostUid(Long postUid) {

        this.postUid = postUid;
    }

    @JsonIgnore
    public User getUser() {

        return this.user;
    }

    public Long getUserUid() {

        return this.user.getUserUid();
    }

    public String getUserEmail() {

        return user.getEmail();
    }

    public String getUserName() {

        return user.getUserName();
    }

    public void setUser(User user) {

        this.user = user;
    }

    public PostType getPostType() {

        return postType;
    }

    public void setPostType(PostType postType) {

        this.postType = postType;
    }

    public Date getCreatedDate() {

        return createdDate;
    }

    public String getCreatedDateStr() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.createdDate);
    }

    public void setCreatedDate(Date createdDate) {

        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {

        return modifiedDate;
    }

    public String getModifiedDateStr() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(this.modifiedDate);
    }

    public void setModifiedDate(Date modifiedDAte) {

        this.modifiedDate = modifiedDAte;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getContents() {

        return contents;
    }

    public void setContents(String contents) {

        this.contents = contents;
    }

    public List<PostFile> getPostFiles() {

        return postFiles;
    }

    public void addPostFiles(List<PostFile> postFiles) {

        postFiles.forEach(i -> i.setPost(this));
        this.postFiles.addAll(postFiles);
    }

    @Override
    public String toString() {

        return String.format("{\"uid\":\"%s\", \"title\":\"%s\"}", this.postUid, this.title);
    }
}
