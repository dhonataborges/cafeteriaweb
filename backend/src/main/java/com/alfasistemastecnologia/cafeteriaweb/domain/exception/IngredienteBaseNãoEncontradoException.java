package com.alfasistemastecnologia.cafeteriaweb.domain.exception;

public class IngredienteBaseNãoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public IngredienteBaseNãoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public IngredienteBaseNãoEncontradoException(Long ingredienteId) {
        this(String.format("Não existe um ingrediente base com o código %d.", ingredienteId));
    }

}
