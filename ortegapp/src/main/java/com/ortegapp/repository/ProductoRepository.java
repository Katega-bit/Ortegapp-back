package com.ortegapp.repository;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {

    @Query("""
            SELECT p FROM Producto p WHERE p.tipo =:tipo 
            """)
    Page<Producto> findProductosTipo(@Param("tipo") String tipo, Pageable pageable);

}
