package com.board.api.service.impl;

import com.board.api.service.UserService;
import com.board.common.entity.User;
import com.board.common.repository.UserRepository;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {

        this.repository = repository;
    }

    @Override
    public Optional<User> getItemByUid(Long uid) {

        return repository.findById(uid);
    }

    @Override
    public User createItem(User user) {

        if (repository.existsByUserId(user.getUserId())) {
            throw new HibernateException("User Already Exist!");
        }

        return repository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public User updateItem(User user) {

        if (!repository.existsByUserId(user.getUserId())) {
            throw new HibernateException("User Do Not Exist!");
        }

        return repository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void deleteItem(Long uid) {

        if (!repository.existsById(uid)) {
            throw new HibernateException("User Do Not Exist!");
        }

        repository.deleteById(uid);
    }

    @Transactional
    public void deleteUser(String userId) {

        if (!repository.existsByUserId(userId)) {
            throw new HibernateException("User Do Not Exist!");
        }

        repository.deleteByUserId(userId);
    }
}
