package com.ortegapp.model.dto.producto;

import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.comentario.ComentarioResponse;
import com.ortegapp.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {

    private Long id;
    @NotEmpty
    private String nombre;

    @URL
    private String foto;

    @NotEmpty
    private String tipo;
    @NotEmpty
    private String descripcion;
    @Min(value = 0)
    private double precio;
    private Set<UserResponse> likes;

    private List<ComentarioResponse> comentarios;

    public static ProductoResponse toProductoResponse(Producto producto){
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .foto(producto.getFoto())
                .tipo(producto.getTipo())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .likes(producto.getLikes().stream().map(UserResponse::fromUser).collect(Collectors.toSet()))
                .comentarios(producto.getComentarios().stream().map(ComentarioResponse::toComentario).toList())
                .build();

    }




}
