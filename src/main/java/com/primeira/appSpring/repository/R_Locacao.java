package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_ViewLocacao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            "(NOW() between l.check_in and l.check_out) and no_show = false " +
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

    @Query(value = "SELECT \n" +
            "    l.id, \n" +
            "    l.id_usuario, \n"+
            "    q.numero_quarto, \n" +
            "    l.diaria, \n" +
            "    l.senha, \n" +
            "    l.check_in, \n" +
            "    l.check_out,\n" +
            "    CASE \n" +
            "        WHEN CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) = 0 THEN 1 \n" +
            "        ELSE CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) \n" +
            "    END AS dias_estadia,  \n" +
            "    COALESCE(SUM(c.preco * c.quantidade), 0) AS total_consumo,\n" +
            "    l.no_show,\n" +
            "    l.is_checked\n" +
            "FROM hotel.locacao l\n" +
            "JOIN hotel.quartos q \n" +
            "    ON l.id_quarto = q.id\n" +
            "LEFT JOIN hotel.consumo_locacao c \n" +
            "    ON l.id = c.id_locacao\n" +
            "AND c.id_produto not in(9) \n" +
            "WHERE l.id_usuario = :id_usuario\n" +
            "AND NOW() BETWEEN l.check_in AND l.check_out\n" +
            "AND l.no_show = false\n" +
            "GROUP BY l.id, \n" +
            "    l.id_usuario, \n" +
            "    q.numero_quarto, \n" +
            "    l.diaria, \n" +
            "    l.senha, \n" +
            "    l.check_in, \n" +
            "    l.check_out,\n" +
            "    l.no_show,\n" +
            "    l.is_checked\n" +
            "ORDER BY l.check_in asc;\n", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesEmCurso(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT l.id, \n" +
            "    l.id_usuario, \n" +
            "    q.numero_quarto, \n" +
            "    l.diaria, \n" +
            "    l.senha, \n" +
            "    l.check_in, \n" +
            "    l.check_out,\n" +
            "    CASE \n" +
            "        WHEN CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) = 0 THEN 1 \n" +
            "        ELSE CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) \n" +
            "    END AS dias_estadia,  \n" +
            "    COALESCE(SUM(c.preco * c.quantidade), 0) AS total_consumo,\n" +
            "    l.no_show,\n" +
            "    l.is_checked \n" +
            "FROM hotel.locacao l\n" +
            "JOIN hotel.quartos q \n" +
            "    ON l.id_quarto = q.id\n" +
            "LEFT JOIN hotel.consumo_locacao c \n" +
            "    ON l.id = c.id_locacao\n" +
            "WHERE l.id_usuario = :id_usuario\n" +
            "    AND NOW() < l.check_in\n" +
            "GROUP BY l.id, \n" +
            "    l.id_usuario, \n" +
            "    q.numero_quarto, \n" +
            "    l.diaria, \n" +
            "    l.senha, \n" +
            "    l.check_in, \n" +
            "    l.check_out,\n" +
            "    l.no_show,\n" +
            "    l.is_checked\n" +
            "ORDER BY q.numero_quarto;\n", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesFuturas(@Param("id_usuario") Long id_usuario);

    @Query(value = "SELECT l.id, \n" +
            "       l.id_usuario, \n" +
            "       q.numero_quarto, \n" +
            "       l.diaria, \n" +
            "       l.senha, \n" +
            "       l.check_in, \n" +
            "       l.check_out,\n" +
            "       CASE \n" +
            "           WHEN CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) = 0 THEN 1 \n" +
            "           ELSE CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) \n" +
            "       END AS dias_estadia,  \n" +
            "       COALESCE(SUM(c.preco * c.quantidade), 0) AS total_consumo,\n" +
            "       l.no_show,\n" +
            "       l.is_checked \n" +
            "FROM hotel.locacao l\n" +
            "JOIN hotel.quartos q ON l.id_quarto = q.id\n" +
            "LEFT JOIN hotel.consumo_locacao c ON l.id = c.id_locacao\n" +
            "AND c.id_produto not in(9) \n" +
            "WHERE l.id_usuario = ? \n" +
            "AND (NOW() > l.check_out or l.no_show)\n" +
            "GROUP BY l.id, \n" +
            "         l.id_usuario, \n" +
            "         q.numero_quarto, \n" +
            "         l.diaria, \n" +
            "         l.senha, \n" +
            "         l.check_in, \n" +
            "         l.check_out,\n" +
            "         l.no_show,\n" +
            "         l.is_checked\n" +
            "ORDER BY l.check_out desc;\n", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesPassadas(@Param("id_usuario") Long id_usuario);

    @Query(value = "select * from hotel.locacao\n" +
            "where (cast(check_out as date) > now() or cast(check_out as date) = cast(check_in as date))\n" +
            "and now() between check_in and check_out;", nativeQuery = true)
    List<M_Locacao> getLocacoesGerarDiarias();

    @Query(value = "select * from hotel.locacao " +
            "where now() between check_in and check_out " +
            "and is_checked = false;", nativeQuery = true)
    List<M_Locacao> getLocacaoesGerarNoShow();

    @Query(value = "select * from hotel.locacao " +
            "where cast(check_in as date) = current_date + interval '1 day';", nativeQuery = true)
    List<M_Locacao> findLocacoesAmanha();

    @Query(value = "SELECT l.id, \n" +
            "       l.id_usuario, \n" +
            "       q.numero_quarto, \n" +
            "       l.diaria, \n" +
            "       l.senha, \n" +
            "       l.check_in, \n" +
            "       l.check_out,\n" +
            "       CASE \n" +
            "           WHEN CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) = 0 THEN 1 \n" +
            "           ELSE CAST(l.check_out AS DATE) - CAST(l.check_in AS DATE) \n" +
            "       END AS dias_estadia,  \n" +
            "       COALESCE(SUM(c.preco * c.quantidade), 0) AS total_consumo,\n" +
            "       l.no_show,\n" +
            "       l.is_checked\n" +
            "FROM hotel.locacao l\n" +
            "JOIN hotel.quartos q ON l.id_quarto = q.id\n" +
            "LEFT JOIN hotel.consumo_locacao c ON l.id = c.id_locacao\n" +
            "AND c.id_produto not in(9) \n" +
            "WHERE (NOW() > l.check_out and l.is_checked and l.recebeu_ficha is false)\n" +
            "GROUP BY l.id, \n" +
            "         l.id_usuario, \n" +
            "         q.numero_quarto, \n" +
            "         l.diaria, \n" +
            "         l.senha, \n" +
            "         l.check_in, \n" +
            "         l.check_out,\n" +
            "         l.no_show,\n" +
            "         l.is_checked \n" +
            "ORDER BY l.check_out desc;\n", nativeQuery = true)
    List<M_ViewLocacao> findLocacoesRealizadasSucesso();

    @Transactional
    @Modifying
    @Query(value = "update hotel.locacao " +
            "set no_show = true " +
            "where id = :id;", nativeQuery = true)
    void updateNoShowLocacao(@Param("id") long id_locacao);

    @Transactional
    @Modifying
    @Query(value = "update hotel.locacao " +
            "set is_checked = true " +
            "where id = :id;", nativeQuery = true)
    void updateIsCheckedLocacao(@Param("id") long id_locacao);

    @Transactional
    @Modifying
    @Query(value = "update hotel.locacao " +
    "set recebeu_ficha = true " +
    "where id = :id;", nativeQuery = true)
    void updateRecebeuFichaLocacao(@Param("id") long id_locacao);
}
