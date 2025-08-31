package com.petracker.framework.service;

import com.petracker.framework.dto.UserPostDTO;
import com.petracker.framework.dto.UserGetDTO;
import com.petracker.framework.models.User;
import com.petracker.framework.repository.UserRepository;
import com.petracker.framework.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;
    public User getUserById(int id) throws ResourceNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!!"));
    }
    public UserGetDTO convertUserToUserDTO(User user){
        return modelMapper.map(user, UserGetDTO.class);
    }

    public void addUser(UserPostDTO userPostDTO) {
        User user = new User();

        user.setMailId(userPostDTO.getMailId());
        user.setMobileNo(userPostDTO.getMobileNo());
        user.setName(userPostDTO.getName());
        user.setPassword(passwordEncoder.encode(userPostDTO.getPassword()));
        repository.save(user);
    }
    public User findByMailId(String mailId)throws ResourceNotFoundException {
        return repository.findByMailId(mailId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found!!"));
    }
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
