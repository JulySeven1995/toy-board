package com.board.common.entity;

import com.board.common.converter.UserTypeConverter;
import com.board.common.type.UserType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_UID")
    private Long userUid;

    @Column(name = "USER_ID", unique = true)
    private String userId;

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

    public User() {

    }

    public User(String userId, String userName, String password) {

        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public User(String userId, String userName, String password, UserType userType) {

        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getPassword() {

        return password;
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
