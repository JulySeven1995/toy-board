package com.board.api.service.impl;

import com.board.api.dto.UserForm;
import com.board.api.service.UserService;
import com.board.common.entity.User;
import com.board.common.repository.UserRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Qualifier("userDetailsService")
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

        if (repository.existsByEmail(user.getEmail())) {
            throw new HibernateException("User Already Exist!");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        user.setPassword(encoder.encode(user.getPassword()));
        return repository.saveAndFlush(user);
    }

    @Transactional
    @Override
    public User updateItem(User user) {

        if (!repository.existsByEmail(user.getEmail())) {
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
    @Override
    public void deleteUser(String Email) {

        if (!repository.existsByEmail(Email)) {
            throw new HibernateException("User Do Not Exist!");
        }

        repository.deleteByEmail(Email);
    }

    @Transactional
    @Override
    public User signUpUser(UserForm userForm) {

        User user = new User(userForm.getEmail(), userForm.getUserName(), userForm.getPassword());

        return this.createItem(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {

        return repository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
