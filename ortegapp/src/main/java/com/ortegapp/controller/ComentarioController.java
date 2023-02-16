package com.ortegapp.controller;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.comentario.ComentarioResponse;
import com.ortegapp.model.dto.comentario.CreateComentario;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/producto")
@RestController
@RequiredArgsConstructor
public class ComentarioController {

    private final ProductoService productoService;

    @PostMapping("/{id}/comentario")
    public ResponseEntity<ProductoResponse> postComentario(@PathVariable Long id, @RequestBody CreateComentario createComentario, @AuthenticationPrincipal User user){
        Producto nuevo = productoService.comentario(id, createComentario, user);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(ProductoResponse.toProductoResponse(nuevo));
    }

    @GetMapping("/{id}/comentario")
    public List<ComentarioResponse> getAll(){
        List<ComentarioResponse> comentariosResponse= new ArrayList<>();
        productoService.findAllComentarios().forEach(comentario -> {
            comentariosResponse.add(ComentarioResponse.toComentario(comentario));
        });

        return comentariosResponse;
    }


}
