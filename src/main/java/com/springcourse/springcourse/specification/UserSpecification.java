package com.springcourse.springcourse.specification;

import com.springcourse.springcourse.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification {

    public static Specification<User> search(String text) {
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (text == null || text.trim().length() <= 0)
                    return null;

                String likeTerm = "%" + text + "%";

                Predicate predicate =
                        criteriaBuilder.or(criteriaBuilder.like(root.get("name"), likeTerm),
                                criteriaBuilder.or(criteriaBuilder.like(root.get("email"), likeTerm),
                                        criteriaBuilder.or(criteriaBuilder.like(root.get("role").as(String.class), likeTerm))));
                return predicate;
            }
        };
    }
}
