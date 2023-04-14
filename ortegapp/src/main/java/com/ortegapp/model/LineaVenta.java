package com.ortegapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineaVenta {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    private Double descuento;
    private int cant;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;



}
