package com.board.api.service;

import com.board.api.dto.UserForm;
import com.board.common.entity.User;

public interface UserService extends CrudService<User>{

    User signUpUser(UserForm userForm);
}