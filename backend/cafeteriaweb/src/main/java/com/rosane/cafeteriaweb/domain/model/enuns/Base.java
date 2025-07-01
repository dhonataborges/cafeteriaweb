package com.rosane.cafeteriaweb.domain.model.enuns;

import ch.qos.logback.classic.spi.IThrowableProxy;
import lombok.Getter;

@Getter
public enum Base {

    GENERICO(0, "Selecione"),
    EXPRESSO(1, "Expresso"),
    LEITE(2, "Leite"),
    CHOCOLATE(3, "Chocolate"),
    ESPULMA(4,"Espulma");

    private Integer codBase;
    private String descricaoBase;

    private Base(Integer codBase, String descricaoBase) {
        this.codBase = codBase;
        this.descricaoBase = descricaoBase;
    }

    public static Base toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Base base : Base.values()) {
            if (cod.equals(base.getCodBase())) {
                return base;
            }
        }
        throw new IllegalArgumentException("Base Invalida!" + cod);
    }

}
