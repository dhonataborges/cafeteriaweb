package com.alfasistemastecnologia.cafeteriaweb.domain.repository;

import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import com.alfasistemastecnologia.cafeteriaweb.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
