package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
    @Query(value = "select * from hotel.locacao l where senha = :senha and " +
            "(l.check_out <= :check_in or l.check_in >= :check_out) " +
            "limit 1;", nativeQuery = true)
    Optional<M_Locacao> findIfSenhaRepetida(@Param("senha") int senha,
                                            @Param("check_in") LocalDateTime check_in,
                                            @Param("check_out") LocalDateTime check_out); // retorna um objeto null caso não encontre uma linha

    @Query(value = "select * from hotel.locacao l where senha = :senha and " +
            "(NOW() between l.check_in and l.check_out) " +
            "limit 1;", nativeQuery = true)
    Optional<M_Locacao> findIfSenhaIsCorrect(@Param("senha") int senha);

    @Query(value = "select * from hotel.locacao l where id_usuario = :id_usuario and " +
            "NOW() between l.check_in and l.check_out;", nativeQuery = true)
    List<M_Locacao> findActiveLocacaoByUserId(@Param("id_usuario") Long id);

    @Query(value = "select * from hotel.locacao l where l.id_usuario = :id_usuario " +
            "and NOW() > l.check_out;", nativeQuery = true)
    List<M_Locacao> findPastLocacaoByUserId(@Param("id_usuario") Long id);

    @Query(value = "select * from hotel.locacao l where l.id_usuario = :id_usuario " +
            "and NOW() < l.check_in;", nativeQuery = true)
    List<M_Locacao> findReservasByUserId(@Param("id_usuario") Long id);

    @Query(value = "select * from hotel.locacao l " +
            "where l.id_quarto = :id_quarto and " +
            "(l.check_in between :check_in and :check_out or " +
            ":check_in between l.check_in and l.check_out or " +
            ":check_out between l.check_in and l.check_out) " +
            "limit 1;", nativeQuery = true)
    M_Locacao quartoEstaLocado(@Param("id_quarto") Long id,
                               @Param("check_in") LocalDateTime check_in,
                               @Param("check_out") LocalDateTime check_out);
}
