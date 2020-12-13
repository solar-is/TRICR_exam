package com.prosolovich.tricr.exam;

import com.prosolovich.tricr.exam.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
