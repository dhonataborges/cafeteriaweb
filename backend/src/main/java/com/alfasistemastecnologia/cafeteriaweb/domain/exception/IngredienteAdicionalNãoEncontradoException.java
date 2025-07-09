package com.alfasistemastecnologia.cafeteriaweb.domain.exception;

public class IngredienteAdicionalNãoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public IngredienteAdicionalNãoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public IngredienteAdicionalNãoEncontradoException(Long ingredienteId) {
        this(String.format("Não existe um ingrediente adicional com o código %d.", ingredienteId));
    }

}
