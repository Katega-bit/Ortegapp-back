package com.ortegapp.search.spec;

import com.ortegapp.model.Producto;
import com.ortegapp.search.util.SearchCriteria;

import java.util.List;

public class ProductoSpecificationBuilder extends GenericSpecificationBuilder<Producto> {
    public ProductoSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Producto.class);
    }



}
