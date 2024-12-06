package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Usuario;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface R_Quarto extends JpaRepository<M_Quarto, Long> {
    @Query(value = "select * from hotel.quartos where numero_quarto = :numero limit 1", nativeQuery = true)
    M_Quarto findByNumero(@Param("numero") int numero);

    @Query(value = "SELECT q.* " +
            "FROM hotel.quartos q " +
            "WHERE NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM hotel.locacao l " +
            "    WHERE l.id_quarto = q.id " +
            "    AND (l.check_in < :checkOut AND l.check_out > :checkIn) " +
            "    AND l.check_out >= CURRENT_DATE " +
            ") ORDER BY q.numero_quarto;", nativeQuery = true)
    List<M_Quarto> findQuartosDisponiveis(@Param("checkIn") LocalDateTime checkIn, @Param("checkOut") LocalDateTime checkOut);
}
