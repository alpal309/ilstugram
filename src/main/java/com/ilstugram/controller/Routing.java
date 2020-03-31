package com.ilstugram.controller;

import com.ilstugram.model.User;
import com.ilstugram.repository.UserRepository;
import com.ilstugram.utility.UtilFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(method = { RequestMethod.GET })
public class Routing {

    @Autowired
    UserRepository ur;

    @GetMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping(value = "/profile/{username}")
    public ResponseEntity<?> profile(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) throws NullPointerException {
        User user = null;
        response.setHeader("isUser", "false");

        if(UtilFunc.sessionEqualsCookie(request) && UtilFunc.getSessionUser(request).getUsername().equals(username))
            response.setHeader("isUser", "true");

        try{
             user = ur.findUserByUsernameAndEnabled(username, 1);
             if(UtilFunc.isEmpty(user))
                 throw new NullPointerException();
        }catch(NullPointerException npe){
            return new ResponseEntity<>("No user found!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(UtilFunc.gson().toJson(user), HttpStatus.OK);
    }
}