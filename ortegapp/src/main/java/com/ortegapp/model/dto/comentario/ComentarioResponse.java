package com.ortegapp.model.dto.comentario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioResponse {

    private Long id;

    private Producto producto;

    //mmm no se yo...
    private String user;

    private String mensaje;


    public static ComentarioResponse toComentario(Comentario comentario){
        return ComentarioResponse.builder()
                .id(comentario.getId())
                .producto(comentario.getProducto())
                .user(comentario.getUser().getUsername())
                .mensaje(comentario.getMensaje())
                .build();
    }



}
