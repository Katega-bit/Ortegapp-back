package com.ortegapp.repository;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
