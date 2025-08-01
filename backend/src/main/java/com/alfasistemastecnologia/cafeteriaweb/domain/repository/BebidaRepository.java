package com.alfasistemastecnologia.cafeteriaweb.domain.repository;

import com.alfasistemastecnologia.cafeteriaweb.domain.model.Bebida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
}
