package com.ortegapp.service;

import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.comentario.CreateComentario;
import com.ortegapp.model.dto.producto.CreateProduct;
import com.ortegapp.model.dto.producto.EditProducto;
import com.ortegapp.repository.ComentarioRepository;
import com.ortegapp.repository.ProductoRepository;
import com.ortegapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final UserRepository userRepository;
    private final ComentarioRepository comentarioRepository;

    public List<Producto> findAll() {
        List<Producto> result = productoRepository.findAll();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Productos no encontrados");
        }
        return result;
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay producto con id: " + id));
    }

    public Producto edit(Long id, EditProducto p) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(p.getNombre());
                    producto.setTipo(p.getTipo());
                    producto.setDescripcion(p.getDescripcion());
                    producto.setPrecio(p.getPrecio());
                    return productoRepository.save(producto);
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public void delete(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        }

    }

    public Producto save(CreateProduct createProduct) {
        return productoRepository.save(CreateProduct.toProducto(createProduct));

    }

    public Optional<Producto> like(Long id, User user) {


        if (productoRepository.existsById(id)) {

            productoRepository.findById(id).get().getLikes().add(user);
            productoRepository.save(productoRepository.findById(id).get());
            userRepository.findById(user.getId()).get().getLikes().add(productoRepository.findById(id).get());
            userRepository.save(user);
            return productoRepository.findById(id);
        }

        return Optional.empty();

    }

    public Producto comentario(Long id, CreateComentario commentary, User user) {
        if (productoRepository.existsById(id)) {
            productoRepository.findById(id).get().getComentarios().add(
                    comentarioRepository.save(
                            Comentario.builder()
                                    .producto(productoRepository.findById(id).get())
                                    .mensaje(commentary.getTexto())
                                    .user(user)
                                    .build()));
            productoRepository.save(productoRepository.findById(id).get());
            return productoRepository.findById(id).get();
        }
        return null;

    }

}
