package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
    @Query(value = "select * from hotel.locacao where senha = :senha limit 1", nativeQuery = true)
    Optional<M_Locacao> findBySenha(@Param("senha") BigDecimal senha); // retorna um objeto null caso não encontre uma linha
    // com senha repetida
}
