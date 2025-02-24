package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface R_Usuario extends JpaRepository<M_Usuario, Long> {
    @Query(value = "select * from hotel.usuario where usuario = :usuario and senha = :senha limit 1", nativeQuery = true)
    M_Usuario getUsuarioByUsuarioSenha(@Param("usuario") String usuario, @Param("senha") String senha);

    @Query(value = "select * from hotel.usuario where id = :id;", nativeQuery = true)
    M_Usuario findUsuarioById(@Param("id") long id_usuario);
}
