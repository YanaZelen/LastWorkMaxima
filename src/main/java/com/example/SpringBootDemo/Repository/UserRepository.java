package com.example.SpringBootDemo.Repository;

import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
