package com.softdeving.todosimples.repositories;

import com.softdeving.todosimples.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //@Transactional(readOnly = true)
    Optional<User> findByUsername(String username);
}
