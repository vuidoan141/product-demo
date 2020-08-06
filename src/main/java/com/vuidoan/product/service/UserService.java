package com.vuidoan.product.service;

import com.vuidoan.product.dto.UserDTO;
import com.vuidoan.product.entity.User;

import java.util.List;

public interface UserService {
    User findById(int id);
    User findByUsername(String username);
    List<User> findAll();
    void save(User user);
    void deleteById(int id);
}
