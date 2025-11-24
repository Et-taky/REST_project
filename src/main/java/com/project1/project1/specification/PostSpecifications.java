package com.project1.project1.specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import com.project1.project1.model.Post;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

public class PostSpecifications {

    public static Specification<Post> byTags(List<String> tags) {
    return (root, query, cb) -> {
        if (tags == null || tags.isEmpty()) {
            return null; // pas de filtre
        }
        Join<Post, String> tagJoin = root.join("tags");

        CriteriaBuilder.In<String> in = cb.in(cb.lower(tagJoin.as(String.class)));

        tags.forEach(t -> in.value(t.toLowerCase()));

        return in;
    };
}



    public static Specification<Post> byUser(UUID userId) {
    return (root, query, cb) -> {
        if (userId == null) {
            return null; // ne filtre pas
        }
        return cb.equal(root.get("owner").get("id"), userId);
    };
}

public static Specification<Post> byId(UUID postId) {
    return (root, query, cb) -> postId == null ? cb.conjunction() : cb.equal(root.get("id"), postId);
}


    public static Specification<Post> filter(
            String text,
            Integer minLikes,
            Integer maxLikes,
            LocalDate publishDateAfter,
            LocalDate publishDateBefore) {
        
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction(); // Predicat vide de base
        
            
            // Filtre par texte (recherche)
            if (text != null && !text.trim().isEmpty()) {
                predicate = builder.and(predicate, 
                    builder.like(builder.lower(root.get("text")), 
                                "%" + text.toLowerCase() + "%"));
            }
            
            // Filtre par likes minimum
            if (minLikes != null) {
                predicate = builder.and(predicate, 
                    builder.greaterThanOrEqualTo(root.get("likes"), minLikes));
            }
            
            // Filtre par likes maximum
            if (maxLikes != null) {
                predicate = builder.and(predicate, 
                    builder.lessThanOrEqualTo(root.get("likes"), maxLikes));
            }
            
            // Filtre par date après
            if (publishDateAfter != null) {
                predicate = builder.and(predicate, 
                    builder.greaterThanOrEqualTo(root.get("publishDate"), publishDateAfter));
            }
            
            // Filtre par date avant
            if (publishDateBefore != null) {
                predicate = builder.and(predicate, 
                    builder.lessThanOrEqualTo(root.get("publishDate"), publishDateBefore));
            }
            
            return predicate;
        };
    }
    public static Specification<Post> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        String value = "%" + keyword.toLowerCase() + "%";

        return (root, query, cb) -> {
            Join<Post, String> tagsJoin = root.join("tags", JoinType.LEFT);

            return cb.or(
                    cb.like(cb.lower(root.get("text")), value),
                    cb.like(cb.lower(tagsJoin), value)
            );
        };
    }

}
