package com.rosane.cafeteriaweb.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_bebida_classica")
public class BebidaClassica {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_bebida_classica_ingrediente_base",
            joinColumns = @JoinColumn(name = "bebida_classica_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_base_id")
    )
    private List<IngredienteBase> ingredientesBase;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_bebida_classica_ingrediente_adicional",
            joinColumns = @JoinColumn(name = "bebida_classica_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_adicional_id")
    )
    private List<IngredienteAdicional> ingredientesAdicional;

}
