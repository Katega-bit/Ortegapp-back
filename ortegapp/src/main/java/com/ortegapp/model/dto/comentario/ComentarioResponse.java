package com.ortegapp.model.dto.comentario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioResponse {

    private Long id;

    private Producto producto;

    private User user;

    private String mensaje;


    public static ComentarioResponse toComentario(Comentario comentario){
        return ComentarioResponse.builder()
                .id(comentario.getId())
                .producto(comentario.getProducto())
                .user(comentario.getUser())
                .mensaje(comentario.getMensaje())
                .build();
    }
}
