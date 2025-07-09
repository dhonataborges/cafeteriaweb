package com.alfasistemastecnologia.cafeteriaweb.domain.repository;

import com.alfasistemastecnologia.cafeteriaweb.domain.model.IngredienteAdicional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteAdicionalRepository extends JpaRepository<IngredienteAdicional, Long> {
}
