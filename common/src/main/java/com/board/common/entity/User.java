package com.board.common.entity;

import com.board.common.converter.UserTypeConverter;
import com.board.common.type.UserType;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "USER")
public class User implements Serializable, UserDetails, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_UID")
    private Long userUid;
    
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "USER_TYPE")
    @Convert(converter = UserTypeConverter.class)
    private UserType userType = UserType.GENERAL;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @CreationTimestamp
    @Column(name = "LAST_LOGIN_DATE")
    private Date lastLoginDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    public User() {

    }

    public User(String email, String userName, String password) {

        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public User(String email, String userName, String password, UserType userType) {

        this.email = email;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public Long getUserUid() {
        return userUid;
    }

    public void setUserUid(Long userUid) {
        this.userUid = userUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(this.getUserType());
        return list;
    }

    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {

        return  Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return !this.userType.equals(UserType.DORMANT);
    }

    @Override
    public boolean isEnabled() {

        return Boolean.TRUE;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public UserType getUserType() {

        return userType;
    }

    public void setUserType(UserType userType) {

        this.userType = userType;
    }

    public List<Post> getPosts() {

        return posts;
    }

    public void addPost(Post post) {

        post.setUser(this);
        this.posts.add(post);
    }

    public void addPosts(List<Post> posts) {

        this.posts.addAll(posts.stream().peek(post -> post.setUser(this)).collect(Collectors.toList()));
    }

    public void setPosts(List<Post> posts) {

        this.posts = posts.stream().peek(post -> post.setUser(this)).collect(Collectors.toList());
    }

    public Date getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {

        this.createdDate = createdDate;
    }

    public Date getLastLoginDate() {

        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {

        this.lastLoginDate = lastLoginDate;
    }
}
