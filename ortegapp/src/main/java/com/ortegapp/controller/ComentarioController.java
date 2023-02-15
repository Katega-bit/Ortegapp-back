package com.ortegapp.controller;

import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.comentario.CreateComentario;
import com.ortegapp.service.ComentarioService;
import com.ortegapp.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/comentario")
@RestController
@RequiredArgsConstructor
public class ComentarioController {

    private final ProductoService productoService;
    private final ComentarioService comentarioService;

    @PostMapping("/{id}")
    public ResponseEntity<Comentario> postComentario(@PathVariable Long id, @RequestBody CreateComentario createComentario, @AuthenticationPrincipal User user){
        productoService.comentario(id ,CreateComentario.toComentario(createComentario), user);
        Comentario nuevo = comentarioService.save(createComentario);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(nuevo);
    }
}
