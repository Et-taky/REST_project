package com.project1.project1.repository;

import com.project1.project1.model.Post;        
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;


public interface PostDao extends JpaRepository<Post, UUID>,JpaSpecificationExecutor<Post> {

}
