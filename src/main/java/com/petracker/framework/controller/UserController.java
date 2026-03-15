package com.petracker.framework.controller;

import com.petracker.framework.JWTHandler.JwtUtil;
import com.petracker.framework.dto.UserPostDTO;
import com.petracker.framework.models.User;
import com.petracker.framework.response.APIResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.petracker.framework.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "User registration and authentication APIs")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(
            summary = "User login",
            description = "Authenticate with email and password to receive a JWT token. " +
                          "Use the returned token in the **Authorize** button above for secured endpoints."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful – returns JWT token",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
    @RequestBody(
            description = "Login credentials",
            required = true,
            content = @Content(
                    schema = @Schema(type = "object",
                            example = "{\"username\": \"user@example.com\", \"password\": \"yourPassword\"}")
            )
    )
    @PostMapping("/login")
    public ResponseEntity<?> getJWT(@org.springframework.web.bind.annotation.RequestBody Map<String,String> credentials) {
        String email    = credentials.get("username");
        String password = credentials.get("password");
        System.out.println("Email : "+email + " password : "+password);
        try {
            if (email != null && password != null) {
                Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
                User user = (User) auth.getPrincipal();
                String token =  jwtUtil.generateToken(user);
                HashMap<String,String> data = new HashMap<>();
                data.put("Token",token);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new APIResponse("Login Successfull" , data));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIResponse("Invalid Credentials..", null));
            }
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponse("Invalid Credentials..", null));
        }
    }

    @Operation(
            summary = "Register a new user",
            description = "Create a new user account. No authentication required."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "500", description = "User creation failed",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@org.springframework.web.bind.annotation.RequestBody UserPostDTO userPostDTO){
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
