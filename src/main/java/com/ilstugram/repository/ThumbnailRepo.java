package com.ilstugram.repository;

import com.ilstugram.model.Thumbnail;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ThumbnailRepo extends CrudRepository<Thumbnail, String> {

    Thumbnail findThumbnailByUsernameAndEnabled(String username, int enabled);

    @Modifying
    @Query("UPDATE Thumbnail tn SET tn.enabled = ?1 WHERE tn.username = ?2 AND tn.enabled = 1")
    @Transactional(timeout = 5)
    int setEnabledForUsername(int enabled, String username);

}
