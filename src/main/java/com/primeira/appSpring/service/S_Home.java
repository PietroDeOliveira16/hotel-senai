package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Cliente;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Quarto;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Home {
    private static R_Usuario r_usuario;
    private static R_Quarto r_quarto;

    public S_Home(R_Usuario r_usuario, R_Quarto r_quarto){
        this.r_usuario = r_usuario;
        this.r_quarto = r_quarto;
    }

    public static List<M_Usuario> getUsuarios(){
        return r_usuario.findAll();
    }


}
