package com.ortegapp.service;

import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.dto.comentario.CreateComentario;
import com.ortegapp.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;

    public Comentario save(CreateComentario createComentario){
      return   comentarioRepository.save(CreateComentario.toComentario(createComentario));
    }

}
