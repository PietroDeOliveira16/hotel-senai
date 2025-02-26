package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_ApiJson;
import com.primeira.appSpring.model.M_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface R_Produto extends JpaRepository<M_Produto, Long> {
    @Query(value = "select * from hotel.produto where cod_barras = :cod_barras", nativeQuery = true)
    M_Produto findByCodeBar(@Param("cod_barras") String codigo);

    @Query(value = "select * from hotel.produto where id != 9;", nativeQuery = true)
    List<M_Produto> findAllProdutos();

    @Query(value =
            "select p.cod_barras as produto, CASE\n" +
                    "        WHEN (\n" +
                    "            (SELECT COALESCE(SUM(quantidade), 0) FROM hotel.compra WHERE id_produto = p.id AND CAST(data AS date) <= :data)\n" +
                    "            - \n" +
                    "            (SELECT COALESCE(SUM(quantidade), 0) FROM hotel.consumo_locacao WHERE id_produto = p.id AND CAST(data AS date) <= :data)\n" +
                    "        ) < 0 THEN 0\n" +
                    "        ELSE (\n" +
                    "            (SELECT COALESCE(SUM(quantidade), 0) FROM hotel.compra WHERE id_produto = p.id AND CAST(data AS date) <= :data)\n" +
                    "            - \n" +
                    "            (SELECT COALESCE(SUM(quantidade), 0) FROM hotel.consumo_locacao WHERE id_produto = p.id AND CAST(data AS date) <= :data)\n" +
                    "        )\n" +
                    "    END AS quantidade, \n" +
                    "5 as min, 200 as max," +
                    "CASE\n" +
                    "WHEN (\n" +
                    "sum(c.quantidade * c.preco) / sum(c.quantidade)\n" +
                    ") is null then 0\n" +
                    "ELSE (\n" +
                    "sum(c.quantidade * c.preco) / sum(c.quantidade)\n" +
                    ")\n" +
                    "END as custo_medio,\n" +
                    "(\n" +
                    "select cast(data as date) from hotel.compra\n" +
                    "where id_produto = p.id\n" +
                    "order by data desc limit 1\n" +
                    ") as ultima_compra\n" +
                    "from hotel.produto p\n" +
                    "left join hotel.compra c on p.id = c.id_produto and cast(c.data as date) <= :data\n" +
                    "where p.id != 9\n" +
                    "group by p.id, produto, min, max, ultima_compra\n" +
                    "order by p.id;", nativeQuery = true)
    List<M_ApiJson> findEstoqueOfData(@Param("data") LocalDate data);
}
