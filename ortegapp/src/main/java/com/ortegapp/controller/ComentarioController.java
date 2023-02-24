package com.ortegapp.controller;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.comentario.ComentarioResponse;
import com.ortegapp.model.dto.comentario.CreateComentario;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Crea un nuevo comentario a un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo comentario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class),
                            examples = @ExampleObject(value = """
                                                    {
                                                        "id": 2,
                                                        "nombre": "Fresitas",
                                                        "foto": "https://classroom.google.com/u/0/h",
                                                        "tipo": "Gomita",
                                                        "descripcion": "Lorem Ipsum Dolor Sit Amet....",
                                                        "precio": 5.0,
                                                        "likes": [],
                                                        "comentarios": [
                                                        {
                                                                    "id": 3,
                                                                    "producto": "Wine - Port Late Bottled Vintage",
                                                                    "user": "user1",
                                                                    "avatar": null,
                                                                    "fullname": "Carlos Ortega",
                                                                    "mensaje": "estaban caducadas y duras"
                                                                }
                                                        ]
                                                    }
                                    """))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han introducido los datos correctamente",
                    content = @Content),
    })
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

    @Operation(summary = "Este método muestra los comentarios de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el comentario",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)),
                            examples = @ExampleObject(value = """
                                                {
}
                                    """
                            ))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado nigun comentario",
                    content = @Content),
    })
    @GetMapping("/{id}/comentario")
    public List<ComentarioResponse> getAll(){
        List<ComentarioResponse> comentariosResponse= new ArrayList<>();
        productoService.findAllComentarios().forEach(comentario -> {
            comentariosResponse.add(ComentarioResponse.toComentario(comentario));
        });

        return comentariosResponse;
    }

    @DeleteMapping("/{id}/comentario/{id2}")
    public ResponseEntity<?> deleteById(@PathVariable Long idProducto, @PathVariable Long idComentario){
        productoService.deleteComentarioById(idProducto, idComentario);
        return ResponseEntity.noContent().build();
    }


}
