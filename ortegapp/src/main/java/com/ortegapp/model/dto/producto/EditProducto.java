package com.ortegapp.model.dto.producto;

import com.ortegapp.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditProducto {

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String tipo;
    @NotEmpty
    private String descripcion;

    @URL
    private String foto;

    @Min(value = 0, message = "{editProductDto.price.min}")
    private double precio;

    public static Producto toProducto(EditProducto editProducto){
        return Producto.builder()
                .nombre(editProducto.nombre)
                .nombre(editProducto.foto)
                .tipo(editProducto.tipo)
                .descripcion(editProducto.descripcion)
                .precio(editProducto.precio)
                .build();
    }

    public static ProductoResponse toProductoResponse(EditProducto editProducto){
        return ProductoResponse.builder()
                .nombre(editProducto.nombre)
                .tipo(editProducto.tipo)
                .descripcion(editProducto.descripcion)
                .precio(editProducto.precio)
                .build();
    }

}
