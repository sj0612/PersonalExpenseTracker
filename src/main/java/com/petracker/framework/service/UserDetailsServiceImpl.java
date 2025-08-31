package com.petracker.framework.service;

import com.petracker.framework.models.User;
import com.petracker.framework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In UserDetailsServiceImpl : "+username);
        User user = repository.findByMailId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));


        System.out.println("User  : "+user);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found..");
        }
        return user;
    }
}
