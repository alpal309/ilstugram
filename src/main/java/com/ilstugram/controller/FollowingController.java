package com.ilstugram.controller;

import com.ilstugram.model.Following;
import com.ilstugram.model.Image;
import com.ilstugram.repository.FollowRepo;
import com.ilstugram.repository.ImageRepo;
import com.ilstugram.utility.UtilFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FollowingController {

    @Autowired
    FollowRepo fr;

    @Autowired
    ImageRepo ir;

    @PostMapping(value = "/followuser")
    public ResponseEntity<?> followUser (@RequestParam Map<String, String> body){

        return ResponseEntity
                .ok(UtilFunc.gson().toJson(fr.save(new Following(body.get("follower"), body.get("following")))));
    }

    @GetMapping(value = "/getfollowingImages")
    public ResponseEntity<?> getFollowingImages(HttpServletRequest request){
        List<Following> following = fr.findAllByFollowerAndEnabled(UtilFunc.getSessionUser(request).getUsername(), 1);
        List<Image> followersImages = new ArrayList<Image>();

        for (Following value : following) {
            followersImages.addAll(ir.findImageByUsernameAndEnabled(value.getFollowing(), 1));
        }
        return ResponseEntity.ok(UtilFunc.gson().toJson(followersImages));
    }

}
