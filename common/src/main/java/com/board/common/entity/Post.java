package com.board.common.entity;

import com.board.common.converter.PostTypeConverter;
import com.board.common.type.PostType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "POST")
public class Post implements Serializable {

    @Id
    @Column(name = "POST_UID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_UID")
    private User user;

    @Column(name = "POST_TYPE")
    @Convert(converter = PostTypeConverter.class)
    private PostType postType = PostType.NORMAL;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @CreationTimestamp
    @Column(name = "MODIFED_DATE")
    private Date modifiedDate;

    @Column(name = "CONTENTS")
    private String contents;

    public Post() {

    }

    public Post(User user, String contents) {
        this.user = user;
        this.contents = contents;
    }


    public Post(User user, PostType postType, String contents) {

        this.user = user;
        this.postType = postType;
        this.contents = contents;
    }


    public Long getPostUid() {

        return postUid;
    }

    public void setPostUid(Long postUid) {

        this.postUid = postUid;
    }

    public User getUser() {

        return user;
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

    public void setCreatedDate(Date createdDate) {

        this.createdDate = createdDate;
    }

    public Date getModifiedDAte() {

        return modifiedDate;
    }

    public void setModifiedDAte(Date modifiedDAte) {

        this.modifiedDate = modifiedDAte;
    }

    public String getContents() {

        return contents;
    }

    public void setContents(String contents) {

        this.contents = contents;
    }
}
