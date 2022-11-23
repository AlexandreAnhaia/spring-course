package com.springcourse.springcourse.repository;

import com.springcourse.springcourse.domain.Enumeration.Role;
import com.springcourse.springcourse.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u WHERE email = ?1 AND password = ?2")
    Optional<User> login(String email, String password);

    @Transactional(readOnly = false)
    @Modifying
    @Query("UPDATE users u SET u.role = ?2 WHERE u.id = ?1")
    int updateRole(Long id, Role role);

    Optional<User> findByEmail(String email);
}
