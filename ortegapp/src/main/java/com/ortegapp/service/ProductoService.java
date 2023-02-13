package com.ortegapp.service;

import com.ortegapp.model.Producto;
import com.ortegapp.model.dto.producto.CreateProduct;
import com.ortegapp.model.dto.producto.EditProducto;
import com.ortegapp.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepository;

    public List<Producto> findAll(){
        List<Producto> result= productoRepository.findAll();
        if (result.isEmpty()){
            throw new EntityNotFoundException("Productos no encontrados");
        }
        return result;
    }
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No hay producto con id: " + id));
    }
    public Producto edit(Long id, EditProducto p ){
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

    public void delete(Long id){
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        }

    }
    public Producto save(CreateProduct createProduct){
        return productoRepository.save(CreateProduct.toProducto(createProduct));

    }







}
