package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Usuario;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Login {
    @Autowired
    private R_Usuario r_usuario;

    public M_Usuario validaLogin(String usuario, String senha){
        return r_usuario.getUsuarioByUsuarioSenha(usuario,senha);
    }
}
