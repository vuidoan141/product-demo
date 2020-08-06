package com.vuidoan.product.service.impl;

import com.vuidoan.product.entity.User;
import com.vuidoan.product.repository.UserRepository;
import com.vuidoan.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameAndDeletedIsFalse(username).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        User user = this.findById(id);
        if (user != null) {
            user.setDeleted(true);
            userRepository.save(user);
        }
    }
}
