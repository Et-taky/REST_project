package com.project1.project1.specification;

import com.project1.project1.model.Comment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentSpecification {

    public static Specification<Comment> filter(LocalDate startDate, LocalDate endDate){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(startDate != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("publishDate"), startDate));
            }
            if(endDate != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("publishDate"), endDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Comment> byPost(UUID postId) {
        return (root, query, cb) -> {
            if (postId == null) {
                return null;
            }
            return cb.equal(root.get("post").get("id"), postId);
        };
    }
    public static Specification<Comment> byUser(UUID userId) {
        return (root, query, cb) -> {
            if (userId == null) {
                return null;
            }
            return cb.equal(root.get("owner").get("id"), userId);
        };
    }
    public static Specification<Comment> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }
        String value = "%" + keyword.toLowerCase() + "%";

        return (root, query, cb) -> cb.like(
                cb.lower(root.get("message")),
                value
        );
    }
}
