package com.ortegapp.model.dto.producto;

import com.ortegapp.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProduct {

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String tipo;
    @NotEmpty
    private String descripcion;
    @Min(value = 0)
    private double precio;

    public static Producto toProducto(CreateProduct editProducto){
        return Producto.builder()
                .nombre(editProducto.nombre)
                .tipo(editProducto.tipo)
                .descripcion(editProducto.descripcion)
                .precio(editProducto.precio)
                .build();
    }
}
