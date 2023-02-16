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

    @NotEmpty(message = "{product.nombre.empty}")
    private String nombre;
    @NotEmpty(message = "{product.tipo.empty}")
    private String tipo;

    @URL(message = "{product.foto.url}")
    private String foto;

    @NotEmpty(message = "{product.descripcion.empty}")
    private String descripcion;
    @Min(value = 0)
    private double precio;

    private Set<User> likes;


    public static Producto toProducto(CreateProduct editProducto){
        return Producto.builder()
                .nombre(editProducto.getNombre())
                .foto(editProducto.getFoto())
                .tipo(editProducto.getTipo())
                .descripcion(editProducto.getDescripcion())
                .precio(editProducto.getPrecio())
                .likes(editProducto.getLikes())
                .build();
    }
}
