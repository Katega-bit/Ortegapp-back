package com.ortegapp.model.dto.producto;

import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
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
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProduct {

    @NotEmpty(message = "{product.nombre.empty}")
    private String nombre;
    @NotEmpty(message = "{product.tipo.empty}")
    private String tipo;


    @NotEmpty(message = "{product.descripcion.empty}")
    private String descripcion;
    @Min(value = 0)
    private double precio;

    private Set<UserResponse> likes = new HashSet<>();


    public static Producto toProducto(CreateProduct createProduct){
        return Producto.builder()
                .nombre(createProduct.getNombre())
                .tipo(createProduct.getTipo())
                .descripcion(createProduct.getDescripcion())
                .precio(createProduct.getPrecio())
                .likes(createProduct.getLikes().stream().map(UserResponse::fromUserResponse).collect(Collectors.toSet()))
                .comentarios(new ArrayList<>())
                .build();
    }
}
