package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_ViewLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(value = "select * from hotel.locacao l " +
            "where l.id_quarto = :id_quarto and " +
            "(l.check_in between :check_in and :check_out or " +
            ":check_in between l.check_in and l.check_out or " +
            ":check_out between l.check_in and l.check_out) " +
            "limit 1;", nativeQuery = true)
    M_Locacao quartoEstaLocado(@Param("id_quarto") Long id,
                               @Param("check_in") LocalDateTime check_in,
                               @Param("check_out") LocalDateTime check_out);

    @Query(value = "select " +
            "q.numero_quarto, " +
            "l.diaria, " +
            "l.senha, " +
            "l.check_in, " +
            "l.check_out," +
            "case cast(l.check_out as date) - cast(l.check_in as date) when 0 " +
            "then 1 " +
            "else cast(l.check_out as date) - cast(l.check_in as date) " +
            "end as dias_estadia  " +
            "from hotel.locacao l " +
            "join hotel.quartos q " +
            "on l.id_quarto = q.id " +
            "where l.id_usuario = :id_usuario and " +
            "NOW() between l.check_in and l.check_out;", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesEmCurso(@Param("id_usuario") Long id_usuario);

    @Query(value = "select " +
            "q.numero_quarto, " +
            "l.diaria, " +
            "l.senha, " +
            "l.check_in, " +
            "l.check_out," +
            "case cast(l.check_out as date) - cast(l.check_in as date) when 0 " +
            "then 1 " +
            "else cast(l.check_out as date) - cast(l.check_in as date) " +
            "end as dias_estadia  " +
            "from hotel.locacao l " +
            "join hotel.quartos q " +
            "on l.id_quarto = q.id " +
            "where l.id_usuario = :id_usuario and " +
            "NOW() < l.check_in;", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesFuturas(@Param("id_usuario") Long id_usuario);

    @Query(value = "select " +
            "q.numero_quarto, " +
            "l.diaria, " +
            "l.senha, " +
            "l.check_in, " +
            "l.check_out," +
            "case cast(l.check_out as date) - cast(l.check_in as date) when 0 " +
            "then 1 " +
            "else cast(l.check_out as date) - cast(l.check_in as date) " +
            "end as dias_estadia  " +
            "from hotel.locacao l " +
            "join hotel.quartos q " +
            "on l.id_quarto = q.id " +
            "where l.id_usuario = :id_usuario and " +
            "NOW() > l.check_out;", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesPassadas(@Param("id_usuario") Long id_usuario);
}
