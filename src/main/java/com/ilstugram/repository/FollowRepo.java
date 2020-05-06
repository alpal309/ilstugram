package com.ilstugram.repository;

import com.ilstugram.model.Following;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface FollowRepo extends CrudRepository<Following, String> {

    ArrayList<Following> findAllByFollowerAndEnabled(String follower, int enabled);

}
