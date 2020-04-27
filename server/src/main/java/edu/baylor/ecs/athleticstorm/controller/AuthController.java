/*
 * Filename: AuthController.java
 * Author: Andrew Walker
 * Date Last Modified: 4/18/2020
 */

package edu.baylor.ecs.athleticstorm.controller;

import edu.baylor.ecs.athleticstorm.payload.ApiResponse;
import edu.baylor.ecs.athleticstorm.payload.JwtAuthenticationResponse;
import edu.baylor.ecs.athleticstorm.payload.LoginRequest;
import edu.baylor.ecs.athleticstorm.payload.SignUpRequest;
import edu.baylor.ecs.athleticstorm.repository.RoleRepository;
import edu.baylor.ecs.athleticstorm.security.JwtTokenProvider;
import edu.baylor.ecs.athleticstorm.exception.AppException;
import edu.baylor.ecs.athleticstorm.model.auth.Role;
import edu.baylor.ecs.athleticstorm.model.auth.RoleName;
import edu.baylor.ecs.athleticstorm.model.auth.User;
import edu.baylor.ecs.athleticstorm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

/**
 * Controller for Auth data
 *
 * @author Andrew Walker
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    /**
     * Signs in the user
     * @param loginRequest a request to login
     * @return if successful then token
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * Signs up a user given a request
     * @param signUpRequest a sign up request
     * @return if successful
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        Role newRole = roleRepository.findByName(signUpRequest.getRoleName())
                .orElseThrow(() -> new AppException("User Role not set."));

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(newRole);
        roleSet.add(userRole);

        user.setRoles(roleSet);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/subchange")
    public ResponseEntity<?> changeSubscription(@Valid @RequestBody SignUpRequest signUpRequest) {

         if(!userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
             return new ResponseEntity(new ApiResponse(false, "Your username is not registered. Please log out and try again."),
                     HttpStatus.BAD_REQUEST);
         } else{

             User user = userRepository.findByUsername(signUpRequest.getUsername()).get();

             Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                     .orElseThrow(() -> new AppException("User Role not set."));

             Role newRole = roleRepository.findByName(signUpRequest.getRoleName())
                     .orElseThrow(() -> new AppException("User Role not set."));

             Set<Role> roleSet = new HashSet<>();
             roleSet.add(newRole);
             roleSet.add(userRole);

             user.setRoles(roleSet);

             User result = userRepository.save(user);

             URI location = ServletUriComponentsBuilder
                     .fromCurrentContextPath().path("/users/{username}")
                     .buildAndExpand(result.getUsername()).toUri();

             return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
         }
    }
}
