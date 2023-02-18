package com.ortegapp.controller;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.page.PageResponse;
import com.ortegapp.model.dto.producto.CreateProduct;
import com.ortegapp.model.dto.producto.EditProducto;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.search.util.SearchCriteria;
import com.ortegapp.search.util.SearchCriteriaExtractor;
import com.ortegapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/producto")
@RestController
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public PageResponse<ProductoResponse> getAll(@RequestParam(value = "search", defaultValue = "") String search,
    @PageableDefault(size = 20, page = 0) Pageable pageable){

        PageResponse<ProductoResponse> result = productoService.findAll(search, pageable);
        
        return result;
    }

    @GetMapping("/{id}")
    public ProductoResponse getById(@PathVariable Long id){
        return ProductoResponse.toProductoResponse(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> newProducto(@Valid @RequestBody CreateProduct createProduct){
        Producto nuevo = productoService.save(createProduct);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(ProductoResponse.toProductoResponse(nuevo));

    }

    @PutMapping("/{id}")
    public ProductoResponse editProducto(@PathVariable Long id, @RequestBody EditProducto editProducto){
            return ProductoResponse.toProductoResponse(productoService.edit(id, editProducto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id){
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<ProductoResponse> likeProducto(@PathVariable Long id, @AuthenticationPrincipal User user){
        return  ResponseEntity.status(HttpStatus.CREATED).body(ProductoResponse.toProductoResponse( productoService.like(id, user).get()));
    }
}
