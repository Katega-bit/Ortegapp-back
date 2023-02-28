package com.ortegapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Comentario {
    @Id@GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "producto_id")
    @JsonIgnore
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String mensaje;
}
