package com.ortegapp.service;

import com.ortegapp.model.Comentario;
import com.ortegapp.model.Producto;
import com.ortegapp.model.User;
import com.ortegapp.model.dto.comentario.CreateComentario;
import com.ortegapp.model.dto.page.PageResponse;
import com.ortegapp.model.dto.producto.CreateProduct;
import com.ortegapp.model.dto.producto.EditProducto;
import com.ortegapp.model.dto.producto.ProductoResponse;
import com.ortegapp.repository.ComentarioRepository;
import com.ortegapp.repository.ProductoRepository;
import com.ortegapp.repository.UserRepository;
import com.ortegapp.search.spec.GenericSpecificationBuilder;
import com.ortegapp.search.spec.ProductoSpecificationBuilder;
import com.ortegapp.search.util.SearchCriteria;
import com.ortegapp.search.util.SearchCriteriaExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public PageResponse<ProductoResponse> search(List<SearchCriteria> params, Pageable pageable) {
        ProductoSpecificationBuilder genericSpecificationBuilder = new ProductoSpecificationBuilder(params);
        Specification<Producto> spec = genericSpecificationBuilder.build();
        Page<ProductoResponse> productoResponsePage = productoRepository.findAll(spec, pageable).map(ProductoResponse::toProductoResponse);
        return new PageResponse<>(productoResponsePage);

    }

    public PageResponse<ProductoResponse> findAll(String s, Pageable pageable) {
        List<SearchCriteria> params = SearchCriteriaExtractor.extractSearchCriteriaList(s);
        PageResponse<ProductoResponse> res = search(params, pageable);
        if (res.getContent().isEmpty()) {
            throw new EntityNotFoundException("Productos no encontrados");
        }
        return res;
    }

    public List<Comentario> findAllComentarios() {
        List<Comentario> result = comentarioRepository.findAll();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Comentarios no encontrados");
        }
        return result;
    }

    public Comentario findByIdComentario(Long idComent, Long idProd){
        if (productoRepository.existsById(idProd) && comentarioRepository.findById(idComent).get().getProducto().getId() == idProd){
            return comentarioRepository.findById(idComent).get();
        }

        return null;

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
