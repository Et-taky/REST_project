package com.project1.project1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.project1.repository.PostDao;

    @Service
public class TagService {

    @Autowired
    private PostDao postDao;

    public List<String> getAllTags() {
        return postDao.findAll()
                .stream()
                .flatMap(post -> post.getTags().stream())  // récupère tags dans chaque post
                .distinct()                                // enlève les doublons
                .sorted()                                  // trie alphabétiquement
                .toList();
    }
}

