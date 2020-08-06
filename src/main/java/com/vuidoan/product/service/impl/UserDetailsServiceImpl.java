package com.vuidoan.product.service.impl;

import com.vuidoan.product.entity.User;
import com.vuidoan.product.repository.UserRepository;
import com.vuidoan.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String username = user.getUsername();
        String password = user.getPassword();
        Set<GrantedAuthority> grantedAuthoritySet=new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthoritySet);
    }
}
