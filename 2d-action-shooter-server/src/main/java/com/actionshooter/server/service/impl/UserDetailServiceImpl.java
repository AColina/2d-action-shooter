package com.actionshooter.server.service.impl;

import com.actionshooter.server.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = new User(userName);
//        UserEntity user = repository.findByUserName(userName);

//        if (user == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
        return user;
    }

}