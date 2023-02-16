package com.ortegapp.model.dto.producto;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProduct {

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String tipo;

    @URL
    private String foto;

    @NotEmpty
    private String descripcion;
    @Min(value = 0)
    private double precio;

    private Set<User> likes;


    public static Producto toProducto(CreateProduct editProducto){
        return Producto.builder()
                .nombre(editProducto.nombre)
                .foto(editProducto.getFoto())
                .tipo(editProducto.tipo)
                .descripcion(editProducto.descripcion)
                .precio(editProducto.precio)
                .likes(editProducto.likes)
                .build();
    }
}
