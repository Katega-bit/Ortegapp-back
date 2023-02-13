package com.ortegapp.model.dto.producto;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String tipo;
    @NotEmpty
    private String descripcion;
    @Min(value = 0)
    private double precio;
    private Set<User> likes;

    private List<User> comentarios;

    public static ProductoResponse toProductoResponse(Producto producto){
        return ProductoResponse.builder()
                .nombre(producto.getNombre())
                .tipo(producto.getTipo())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .likes(producto.getLikes())
                .build();

    }

}
