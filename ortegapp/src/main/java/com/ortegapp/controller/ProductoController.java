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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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


    @Operation(summary = "Este método muestra las cajas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado las cajas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)),
                            examples = @ExampleObject(value = """
                                                
                            {
                                "content": [
                                    {
                                        "id": 1,
                                        "nombre": "Wine - Port Late Bottled Vintage",
                                        "foto": "http://comsenz.com/eleifend/quam/a/odio.jsp",
                                        "tipo": "Vitz",
                                        "descripcion": "Vivamus tortor.",
                                        "precio": 19.0,
                                        "likes": [],
                                        "comentarios": []
                                    },
                                    {
                                        "id": 4,
                                        "nombre": "Skewers - Bamboo",
                                        "foto": "http://rakuten.co.jp/amet/turpis/elementum/ligula/vehicula.jsp",
                                        "tipo": "Dynabox",
                                        "descripcion": "Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede. Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat. Nulla nisl. Nunc nisl.",
                                        "precio": 7.8,
                                        "likes": [],
                                        "comentarios": []
                                    }
                                ],
                                "totalElements": 13,
                                "totalPages": 1,
                                "page": 0
                            }
                            
                                    """
                            ))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado nigun producto",
                    content = @Content),
    })

    @GetMapping
    public PageResponse<ProductoResponse> getAll(@RequestParam(value = "search", defaultValue = "") String search,
    @PageableDefault(size = 20, page = 0) Pageable pageable){

        PageResponse<ProductoResponse> result = productoService.findAll(search, pageable);
        
        return result;
    }

    @Operation(summary = "Este método muestra un producto por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la caja",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)),
                            examples = @ExampleObject(value = """
                                        {
                                            "id": 1,
                                            "nombre": "Wine - Port Late Bottled Vintage",
                                            "foto": "http://comsenz.com/eleifend/quam/a/odio.jsp",
                                            "tipo": "Vitz",
                                            "descripcion": "Vivamus tortor.",
                                            "precio": 19.0,
                                            "likes": [],
                                            "comentarios": []
                                        }
                                    """
                            ))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado niguna caja con ese ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ProductoResponse getById(@PathVariable Long id){
        return ProductoResponse.toProductoResponse(productoService.findById(id));
    }

    @Operation(summary = "Crea un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado un nuevo producto",
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
                                                        "comentarios": []
                                                    }
                                    """))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han introducido los datos correctamente",
                    content = @Content),
    })
    @PostMapping("/admin")
    public ResponseEntity<ProductoResponse> newProducto( @RequestPart("producto") @Valid CreateProduct createProduct,
     @RequestPart("file") MultipartFile file){
        ProductoResponse nuevo = productoService.save(createProduct, file);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevo.getId()).toUri();


        return ResponseEntity
                .created(createdURI)
                .body(nuevo);

    }
    @Operation(summary = "Actualiza el producto de la lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha actualizado el producto",
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
                                                        "comentarios": []
                                                    }
                                    """))}),
            @ApiResponse(responseCode = "400",
                    description = "No existe el producto o los datos de edicion son incorrectos ",
                    content = @Content),
    })
    @PutMapping("/admin/{id}")
    public ProductoResponse editProducto(@PathVariable Long id, @RequestBody EditProducto editProducto){
            return ProductoResponse.toProductoResponse(productoService.edit(id, editProducto));
    }
    @Operation(summary = "Este método borra un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha encontrado el producto",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductoResponse.class)),
                            examples = @ExampleObject(value = """

                                    """
                            ))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado nigun producto con ese ID",
                    content = @Content),
    })
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id){
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Da like a un producti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha guardado un producto que te gusta",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class),
                            examples = @ExampleObject(value = """
                                                    {
                                                        "id": 1,
                                                        "nombre": "Wine - Port Late Bottled Vintage",
                                                        "foto": "http://comsenz.com/eleifend/quam/a/odio.jsp",
                                                        "tipo": "Vitz",
                                                        "descripcion": "Vivamus tortor.",
                                                        "precio": 19.0,
                                                        "likes": [
                                                            {
                                                                "id": "a9fe0178-8679-1260-8186-79d297490000",
                                                                "username": "user1",
                                                                "avatar": null,
                                                                "fullName": "Carlos Ortega",
                                                                "createdAt": null
                                                            }
                                                        ],
                                                        "comentarios": []
                                                    }
                                    """))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el producto",
                    content = @Content),
    })
    @PostMapping("/like/{id}")
    public ResponseEntity<ProductoResponse> likeProducto(@PathVariable Long id, @AuthenticationPrincipal User user){
        return  ResponseEntity.status(HttpStatus.CREATED).body(ProductoResponse.toProductoResponse( productoService.like(id, user)));
    }

    @Operation(summary = "Devuelve una lista de productos de un solo tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha guardado un producto que te gusta",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "content": [
                                            {
                                                "id": 1,
                                                "nombre": "Fresitas",
                                                "foto": "http://comsenz.com/eleifend/quam/a/odio.jsp",
                                                "tipo": "Gomitas",
                                                "descripcion": "Lorem ipsum",
                                                "precio": 10.0,
                                                "likes": [],
                                                "comentarios": []
                                            },
                                            {
                                                "id": 2,
                                                "nombre": "Gusanitos",
                                                "foto": "http://comsenz.com/eleifend/quam/a/odio.jsp",
                                                "tipo": "Snacks",
                                                "descripcion": "Lorem ipsum",
                                                "precio": 15.0,
                                                "likes": [],
                                                "comentarios": []
                                            }
                                        ],
                                        "totalElements": 5,
                                        "totalPages": 1,
                                        "page": 0
                                    }
                                    """))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el producto",
                    content = @Content),
    })
    @GetMapping("/tipo/{tipo}")
    public Page<Producto> findProductosByTipos(@PageableDefault(size = 3, page = 0) Pageable pageable, @Parameter(name = "tipo",
            description = "Se debe proporcionar el tipo del producto para buscar productos de ese tipo en concreto") @PathVariable String tipo) {
        return productoService.findProductosbytipo(tipo, pageable);
    }
}
