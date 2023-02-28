package com.ortegapp.repository;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("""
            SELECT p FROM Producto p JOIN p.likes l WHERE l.username = :username 
            """)
    Page<Producto> mylikes(@Param("username") String username, Pageable pageable);

}
