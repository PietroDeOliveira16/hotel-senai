package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Home {
    private static R_Usuario r_usuario;
    private static R_Locacao r_locacao;

    public S_Home(R_Usuario r_usuario, R_Locacao r_locacao) {
        this.r_usuario = r_usuario;
        this.r_locacao = r_locacao;
    }

    public static List<M_Usuario> getUsuarios() {
        return r_usuario.findAll();
    }

    public static List<M_Locacao> getLocacoesAtivas(Long id_usuario) {
        return r_locacao.findActiveLocacaoByUserId(id_usuario);
    }

    public static List<M_Locacao> getLocacoesPassadas(Long id_usuario){
        return r_locacao.findPastLocacaoByUserId(id_usuario);
    }

    public static List<M_Locacao> getReservas(Long id_usuario){
        return r_locacao.findReservasByUserId(id_usuario);
    }

}
