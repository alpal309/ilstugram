package com.ilstugram.controller;


import com.ilstugram.model.Thumbnail;
import com.ilstugram.repository.ThumbnailRepo;
import com.ilstugram.utility.UtilFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RestController
public class ThumbnailController {

    @Autowired
    ThumbnailRepo tnrepo;

    private Thumbnail setThumbnail(MultipartFile file, HttpServletRequest request) throws Exception{
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String user = UtilFunc.getSessionUser(request).getUsername();

        try{
            Thumbnail pic = new Thumbnail(user, fileName, file.getContentType(), file.getBytes());

            tnrepo.setEnabledForUsername(0, user);

            return tnrepo.save(pic);
        }catch(IOException io){
            throw new Exception("Failed to store file.");
        }

    }

    @PostMapping("/uploadThumbnail")
    public ResponseEntity<?> uploadThumbnail(MultipartFile file, HttpServletRequest request) throws Exception{
        Thumbnail tn = setThumbnail(file, request);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(tn.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tn.getFileName() + "\"")
                .body(new ByteArrayResource(tn.getData()));
    }

    @GetMapping("/getThumbnail")
    public ResponseEntity<?> getThumbnail(HttpServletRequest request){
        try{
            Thumbnail tn = tnrepo.findThumbnailByUsernameAndEnabled(UtilFunc.getSessionUser(request).getUsername(), 1);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(tn.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tn.getFileName() + "\"")
                    .body(new ByteArrayResource(tn.getData()));

        }catch(NullPointerException e){
            System.out.println("no thumbnail found");
            return null;
        }
    }



}
