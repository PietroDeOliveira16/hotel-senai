package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_ConsumoLocacao;
import com.primeira.appSpring.model.M_ViewVenda;
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

    @Query(value = "SELECT id_produto, COALESCE(SUM(quantidade), 0) AS quantidade FROM hotel.consumo_locacao\n" +
            "where data <= data \n" +
            "group by id_produto;", nativeQuery = true)
    List<M_ViewVenda> findVendaData(@Param("data") String data);
}
