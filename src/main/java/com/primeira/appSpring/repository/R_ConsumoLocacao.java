package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_ConsumoLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface R_ConsumoLocacao extends JpaRepository<M_ConsumoLocacao, Long> {
}
