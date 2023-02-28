package com.ortegapp.service;

import com.ortegapp.files.service.StorageService;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final UserRepository userRepository;
    private final ComentarioRepository comentarioRepository;

    private final StorageService storageService;


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
    @Transactional
    public ProductoResponse save(CreateProduct createProduct, MultipartFile file) {
        String filename = storageService.store(file);

        return ProductoResponse.toProductoResponse(
                productoRepository.save(
                        Producto.builder()
                                .nombre(createProduct.getNombre())
                                .tipo(createProduct.getTipo())
                                .foto(filename)
                                .descripcion(createProduct.getDescripcion())
                                .precio(createProduct.getPrecio())
                                .likes(new HashSet<>())
                                .comentarios(new ArrayList<>())
                                .build()
                )
        );

    }

    public Producto like(Long id, User user) {


        if (productoRepository.existsById(id)) {
            if(productoRepository.findById(id).get().getLikes().contains(user)) {
                productoRepository.findById(id).get().getLikes().remove(user);
                productoRepository.save(productoRepository.findById(id).get());
            }
            else {

                productoRepository.findById(id).get().getLikes().add(user);
                productoRepository.save(productoRepository.findById(id).get());
                userRepository.findById(user.getId()).get().getLikes().add(productoRepository.findById(id).get());
                userRepository.save(user);
            }
            return productoRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException());
        }

        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

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

    public Page<Producto> findProductosbytipo(String tipo, Pageable pageable) {
        Page<Producto> res = productoRepository.findProductosTipo(tipo, pageable);
        return res;
    }

    public void deleteComentarioById(Long idProducto, Long idComentario){
        if (productoRepository.findById(idProducto).get().getComentarios().contains(comentarioRepository.findById(idComentario))){
            comentarioRepository.deleteById(idComentario);
        }

    }

    public PageResponse<ProductoResponse> melike(User user, Pageable pageable) {

        Page<Producto> postOfOneUserByUserName = userRepository.mylikes(user.getUsername(), pageable);
        Page<ProductoResponse> postResponsePage =
                new PageImpl<>
                        (postOfOneUserByUserName.stream().toList(), pageable, postOfOneUserByUserName.getTotalPages()).map(ProductoResponse::toProductoResponse);
        return new PageResponse<>(postResponsePage);
    }



}
