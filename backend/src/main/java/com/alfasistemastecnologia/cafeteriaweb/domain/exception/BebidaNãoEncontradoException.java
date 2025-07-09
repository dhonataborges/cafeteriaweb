package com.alfasistemastecnologia.cafeteriaweb.domain.exception;

public class BebidaNãoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public BebidaNãoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public BebidaNãoEncontradoException(Long cafeId) {
        this(String.format("Não existe um café com o código %d.", cafeId));
    }

}
