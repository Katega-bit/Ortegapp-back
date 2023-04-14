package com.ortegapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue
    private Long id;
    private Double precioFinal;
    private Double iva;
    private String fechaVenta;
    private String fechaEntrega;
    @OneToMany(fetch = FetchType.LAZY)
    private List<LineaVenta> productos =  new ArrayList<>();
}
