package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Quarto;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Home {
    private static R_Usuario r_usuario;
    private static R_Quarto r_quarto;
    private static R_Locacao r_locacao;

    public S_Home(R_Usuario r_usuario, R_Quarto r_quarto, R_Locacao r_locacao){
        this.r_usuario = r_usuario;
        this.r_quarto = r_quarto;
        this.r_locacao = r_locacao;
    }

    public static List<M_Usuario> getUsuarios(){
        return r_usuario.findAll();
    }

    public static List<M_Locacao> getLocacoes(Long id_usuario){return r_locacao.findByUserId(id_usuario);}

}
