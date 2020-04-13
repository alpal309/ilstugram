package com.ilstugram.repository;

import com.ilstugram.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ImageRepo extends CrudRepository<Image, String> {

    ArrayList<Image> findImageByUsernameAndEnabled(String username, int enabled);

}

