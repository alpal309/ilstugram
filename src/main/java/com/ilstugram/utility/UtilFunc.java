package com.ilstugram.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilstugram.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

public class UtilFunc {

    public static boolean hasAcceptableInput(String[] input){
        for (String s : input) {
            if (!s.replaceAll("[^a-zA-Z0-9-_@.!?*#]", "").equals(s))
                return false;
        }
        return true;
    }

    public static boolean isEmpty( Object object ){
        return object == null;
    }

    public static void setSession(HttpServletRequest req, User user) {
        try {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public static User getSessionUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    public static void paramMap(HttpServletRequest req){
        Enumeration<String> params = req.getParameterNames();
        Enumeration<String> headerNames = req.getHeaderNames();

        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            System.out.println("Parameter Name - "+paramName+", Value - "+req.getParameter(paramName));
        }
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            System.out.println("Header Name - " + headerName + ", Value - " + req.getHeader(headerName));
        }
    }

    public static Gson gson(){
        GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}