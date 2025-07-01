package com.rosane.cafeteriaweb.domain.exception;

public class BebidaClassicaNãoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public BebidaClassicaNãoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public BebidaClassicaNãoEncontradoException(Long cafeId) {
        this(String.format("Não existe um Bebida Clássica com o código %d.", cafeId));
    }

}
