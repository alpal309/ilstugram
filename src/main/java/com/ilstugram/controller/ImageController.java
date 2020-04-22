package com.ilstugram.controller;

import com.google.gson.reflect.TypeToken;
import com.ilstugram.model.Comment;
import com.ilstugram.model.Image;
import com.ilstugram.repository.CommentRepo;
import com.ilstugram.repository.ImageRepo;
import com.ilstugram.utility.UtilFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;


@RestController
public class ImageController {

    @Autowired
    ImageRepo ir;

    @Autowired
    CommentRepo cr;

    private final String uploadPath = "c:/ilstugramuploads/";

    @GetMapping(value = "/retrieveImages/{username}")
    public ResponseEntity<?> retrieveImages(@PathVariable String username){
        try{
            ArrayList<Image> images = ir.findImageByUsernameAndEnabled(username, 1);
            Type listType = new TypeToken<ArrayList<Image>>() {}.getType();
            if(images.isEmpty())
                throw new NullPointerException("No images!");

            return ResponseEntity.ok(UtilFunc.gson().toJson(images, listType));

        }catch(NullPointerException npe){
            return new ResponseEntity<>(npe.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/uploadImage")
    public ResponseEntity<?> uploadImage(HttpServletRequest request, @RequestParam MultipartFile file){
        try{
            if(!UtilFunc.hasAcceptableInput(new String[]{file.getName(), file.getOriginalFilename()}))
                throw new IOException("Bad values in file name detected.");

            String fileName = new Date(System.currentTimeMillis()).getTime() + "_" + file.getOriginalFilename();
            String username = UtilFunc.getSessionUser(request).getUsername();
            String customPath = uploadPath + "/" + username + "/";

            boolean newDir = new File(customPath).mkdir();
            Path path = Paths.get(customPath + fileName);

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Image img = ir.save(new Image(username, fileName, file.getContentType(), path.toString()));

            return ResponseEntity.ok(UtilFunc.gson().toJson(img, Image.class));
        }catch(IOException io){
            return new ResponseEntity<>(io.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/postComment")
    public ResponseEntity<?> postComment(HttpServletRequest request, @RequestParam String description, @RequestParam String id){
        String username = UtilFunc.getSessionUser(request).getUsername();
        Comment comment = new Comment(id, username, description, new Date());
        return ResponseEntity.ok(UtilFunc.gson().toJson(cr.save(comment)));
    }

}

