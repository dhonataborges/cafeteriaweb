package com.rosane.cafeteriaweb.domain.repository;

import com.rosane.cafeteriaweb.domain.model.Bebida;
import com.rosane.cafeteriaweb.domain.model.BebidaClassica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BebidaClassicaRepository extends JpaRepository<BebidaClassica, Long> {
}
