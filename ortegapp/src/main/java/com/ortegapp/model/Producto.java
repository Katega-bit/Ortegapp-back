package com.ortegapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String tipo;
    private String descripcion;
    private double precio;
    @JoinTable(
            name = "rel_producto_user",
            joinColumns = @JoinColumn(name = "FK_PRODUCT", nullable = false),
            inverseJoinColumns = @JoinColumn(name="FK_USER", nullable = false)
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> likes;
    @OneToMany
    private List<Comentario> comentarios;
}
