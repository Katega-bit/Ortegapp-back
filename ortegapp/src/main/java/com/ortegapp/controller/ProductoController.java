package com.ortegapp.controller;

import com.ortegapp.model.Producto;
import com.ortegapp.model.dto.producto.CreateProduct;
import com.ortegapp.model.dto.producto.EditProducto;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
@RequestMapping("/producto")
@RestController
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponse> getAll(){

        List<ProductoResponse> productoListResponse = new ArrayList<>();
        productoService.findAll().forEach(producto -> {
            productoListResponse.add(ProductoResponse.toProductoResponse(producto));
        });

        return productoListResponse;
    }

    @GetMapping("/{id}")
    public ProductoResponse getById(@PathVariable Long id){
        return ProductoResponse.toProductoResponse(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> newProducto(@Valid @RequestBody CreateProduct createProduct){
        Producto nuevo = productoService.save(createProduct);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(nuevo);

    }

    @PutMapping("/{id}")
    public ProductoResponse editProducto(@PathVariable Long id, @RequestBody EditProducto editProducto){
            return ProductoResponse.toProductoResponse(productoService.edit(id, editProducto));
    }
}
