package com.petracker.framework.controller;

import com.petracker.framework.JWTHandler.JwtUtil;
import com.petracker.framework.dto.UserPostDTO;
import com.petracker.framework.dto.UserGetDTO;
import com.petracker.framework.exceptions.ResourceNotFoundException;
import com.petracker.framework.models.User;
import com.petracker.framework.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.petracker.framework.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


//    @GetMapping("/get/{userId}")
//    public ResponseEntity<?> getUserById(@PathVariable("userId") Integer userid) {
//        try {
//            UserGetDTO user = userService.convertUserToUserDTO(userService.getUserById(userid));
//            return ResponseEntity.status(HttpStatus.OK).body(user);
//        } catch (ResourceNotFoundException exception) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> getJWT(@RequestBody Map<String,String> credentials) {
        String email    = credentials.get("username");
        String password = credentials.get("password");
        System.out.println("Email : "+email + " password : "+password);
        try {
            if (email != null && password != null) {
                Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                User user = (User) auth.getPrincipal();
                String token =  jwtUtil.generateToken(user);
                HashMap<String,String> data = new HashMap();
                data.put("Token",token);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new APIResponse("Login Successfull" , data));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse("Invalid Credentials..", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse("Invalid Credentials..", null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody UserPostDTO userPostDTO){
        try {
            System.out.println("DTO => "+ userPostDTO);
            userService.addUser(userPostDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse("User Created Successfully", userPostDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("User creation failed..." , e.getMessage()));
        }
    }

}
