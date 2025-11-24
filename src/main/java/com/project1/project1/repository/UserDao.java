package com.project1.project1.repository;

import com.project1.project1.model.Comment;
import com.project1.project1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserDao  extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    
}
