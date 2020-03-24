package com.ilstugram.controller;

import com.google.gson.Gson;
import com.ilstugram.model.User;
import com.ilstugram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.ilstugram.utility.UtilFunc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
public class Login {
    private User user;
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/createUser")
    public ResponseEntity<?> createUser(@RequestParam Map<String, String> body, HttpServletRequest request, HttpServletResponse response){
        if(!UtilFunc.hasAcceptableInput(body.values().toArray(new String[0])))
            return new ResponseEntity<>("Invalid input.", HttpStatus.NOT_ACCEPTABLE);


        user = UtilFunc.gson().fromJson(body.toString(), User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        String jsonUser = UtilFunc.gson().toJson(user);
        UtilFunc.setSession(request, user);

        response.setHeader("Location", "/profile.html");
        return new ResponseEntity<>(jsonUser, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestParam Map<String, String> body, HttpServletResponse response) {
        String username = body.get("username");
        String password = body.get("password");
        boolean validPW;

        if(!UtilFunc.hasAcceptableInput(new String[]{username, password}))
            return new ResponseEntity <>("Invalid input.", HttpStatus.NOT_ACCEPTABLE);

        try{
            user = userRepo.findUserByUsernameAndEnabled(username,1);
            validPW = passwordEncoder.matches(password, user.getPassword());
            if(!UtilFunc.isEmpty(user) && validPW){
                String jsonUser = UtilFunc.gson().toJson(user);
                UtilFunc.setSession(request, user);
                response.setHeader("Location", "/feed.html");
                return new ResponseEntity<>(jsonUser, HttpStatus.ACCEPTED);
            }
        }catch(Exception e){
            System.out.println(e);
        }

        return new ResponseEntity <>("Username or password does not match.", HttpStatus.BAD_REQUEST);
    }

}
