package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface R_ValidaSenha extends JpaRepository<M_Locacao, Long> {
    @Query(value = "select * from hotel.locacao where senha = :senha and now() between check_in and check_out limit 1",
            nativeQuery = true)
    M_Locacao getValidacaoBySenha(@Param("senha") BigDecimal senha);
}
