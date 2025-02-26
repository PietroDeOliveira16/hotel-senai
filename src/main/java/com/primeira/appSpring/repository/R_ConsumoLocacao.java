package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_ConsumoLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface R_ConsumoLocacao extends JpaRepository<M_ConsumoLocacao, Long> {
    @Query(value = "select * from hotel.consumo_locacao where id_locacao = :idLocacao " +
            "order by data desc;", nativeQuery = true)
    List<M_ConsumoLocacao> findConsumoLocacao(@Param("idLocacao") long idLocacao);
}
