package com.alfasistemastecnologia.cafeteriaweb.domain.exception;

public class UsuarioNãoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public UsuarioNãoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNãoEncontradoException(Long usuarioId) {
        this(String.format("Não existe um usuário com o código %d.", usuarioId));
    }

}
