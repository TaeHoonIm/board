package com.example.board.domain.post.service;

import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.exception.NotFoundPostException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecifications {

    public static Specification<Post> searchByTypeAndKeyword(String type, String keyword) {
        return (Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (type.equals("title")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("title"), "%" + keyword + "%"));
            } else if (type.equals("content")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("content"), "%" + keyword + "%"));
            } else if (type.equals("titleAndContent")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%"))
                );
            } else if (type.equals("writer")) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        root.get("member").get("nickname"), "%" + keyword + "%")
                );
            } else {
                throw new NotFoundPostException();
            }

            return predicate;
        };
    }
}
