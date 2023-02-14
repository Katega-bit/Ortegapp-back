package com.ortegapp.repository;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
