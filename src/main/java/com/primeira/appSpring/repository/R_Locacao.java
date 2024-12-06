package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
    @Query(value = "select * from hotel.status_locacao where senha = :senha and is_active = true limit 1", nativeQuery = true)
    Optional<M_Locacao> findBySenha(@Param("senha") BigDecimal senha); // retorna um objeto null caso não encontre uma linha
    // com senha repetida

    @Query(value = "select * from hotel.locacao l where id_usuario = :id_usuario and " +
            "l.check_out >= CURRENT_DATE ", nativeQuery = true)
    List<M_Locacao> findByUserId(@Param("id_usuario") Long id);

    @Query(value = "select * from hotel.status_locacao where :checkOut is between check_in and check_out or :checkIn is between check_in and check_out or :checkIn = check_in or :checkOut = check_out", nativeQuery = true)
    M_Locacao findLocacaoInThisTime(@Param("checkIn") LocalDateTime checkIn, @Param("checkOut") LocalDateTime checkOut);
}
