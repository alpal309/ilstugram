package com.ilstugram.repository;

import com.ilstugram.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findUserByUsernameAndEnabled(String username, int enabled);
    User findUserByUsernameAndPasswordAndEnabled(String username, String password, int enabled);
}
