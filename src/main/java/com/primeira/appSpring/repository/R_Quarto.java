package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface R_Quarto extends JpaRepository<M_Quarto, Long> {
    @Query(value = "select * from hotel.quartos where numero_quarto = :numero limit 1", nativeQuery = true)
    M_Quarto findByNumero(@Param("numero") int numero);

    @Query(value = "SELECT q.* FROM hotel.quartos q " +
            "LEFT JOIN hotel.status_locacao sl ON q.id = sl.id_quarto " +
            "WHERE sl.id_quarto IS NULL OR sl.is_active = false;", nativeQuery = true)
    List<M_Quarto> findQuartosDisponiveis();

}
