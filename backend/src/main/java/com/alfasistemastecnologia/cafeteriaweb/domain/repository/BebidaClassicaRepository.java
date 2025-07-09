package com.alfasistemastecnologia.cafeteriaweb.domain.repository;

import com.alfasistemastecnologia.cafeteriaweb.domain.model.BebidaClassica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BebidaClassicaRepository extends JpaRepository<BebidaClassica, Long> {
}
