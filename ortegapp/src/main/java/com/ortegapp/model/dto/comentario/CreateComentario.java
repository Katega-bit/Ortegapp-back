package com.ortegapp.model.dto.comentario;

import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateComentario {

    @NotEmpty
    private String texto;



}
