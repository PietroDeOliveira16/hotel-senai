package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface R_Quarto extends JpaRepository<M_Quarto, Long> {
    @Query(value = "select * from hotel.quartos where numero_quarto = :numero limit 1", nativeQuery = true)
    M_Quarto findByNumero(@Param("numero") int numero);
}
