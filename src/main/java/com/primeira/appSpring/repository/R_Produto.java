package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Produto extends JpaRepository<M_Produto, Long> {
    @Query(value = "select * from hotel.produto where cod_barras = :cod_barras", nativeQuery = true)
    M_Produto findByCodeBar(@Param("cod_barras") String codigo);
}
