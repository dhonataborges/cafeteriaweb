package com.rosane.cafeteriaweb.domain.repository;

import com.rosane.cafeteriaweb.domain.model.IngredienteAdicional;
import com.rosane.cafeteriaweb.domain.model.IngredienteBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteAdicionalRepository extends JpaRepository<IngredienteAdicional, Long> {
}
