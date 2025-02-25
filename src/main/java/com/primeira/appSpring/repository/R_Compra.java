package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Compra;
import com.primeira.appSpring.model.M_ViewCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface R_Compra extends JpaRepository<M_Compra, Long> {
    @Query(value = "SELECT id_produto, COALESCE(sum(quantidade), 0) as quantidade FROM hotel.compra\n" +
            "where data <= :data\n" +
            "group by id_produto;", nativeQuery = true)
    List<M_ViewCompra> findQuantidadeComprasData(@Param("data") String data);
}
