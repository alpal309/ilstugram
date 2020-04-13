package com.ilstugram.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilstugram.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
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

    public static Cookie setSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        System.out.println("logging in: " + req.getSession().getAttribute("user"));
        Cookie c = new Cookie("username", user.getUsername());
        //c.setHttpOnly(true);
        c.setMaxAge(-1);
        return c;
    }

    public static void invalidateSession(HttpServletRequest request){
        System.out.println("logging out: " + request.getSession().getAttribute("user"));
        request.getSession().invalidate();
        Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("username")).forEach(cookie -> {cookie.setMaxAge(0); cookie.setValue("");});
    }

    public static User getSessionUser(HttpServletRequest req) {
        try {
            return (User) req.getSession().getAttribute("user");
        }catch(NullPointerException npe){
            return null;
        }
    }

    public static boolean sessionEqualsCookie(HttpServletRequest request){
        try{
            Cookie usernameCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("username")).toArray(Cookie[]::new)[0];
            return usernameCookie.getValue().equals(UtilFunc.getSessionUser(request).getUsername());
        }catch(NullPointerException npe){
            return false;
        }
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
        GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss");
        return builder.create();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}