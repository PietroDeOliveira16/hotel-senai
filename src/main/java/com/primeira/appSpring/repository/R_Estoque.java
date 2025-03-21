package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Estoque;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Estoque extends JpaRepository<M_Estoque, Long> {
    @Query(value = "select (\n" +
            "\tselect COALESCE(sum(quantidade), 0) from hotel.estoque where cod_barras = :cod\n" +
            ") - (\n" +
            "\tselect COALESCE(sum(quantidade), 0) from hotel.consumo_locacao where cod_barras = :cod\n" +
            ") from hotel.estoque where cod_barras = :cod group by cod_barras;\n", nativeQuery = true)
    Long pegarEstoqueHotel(@Param("cod") String codBarras);
}
