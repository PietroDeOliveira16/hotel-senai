package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.model.M_ViewLocacao;
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

    public static List<M_ViewLocacao> getLocacoesAtivas(Long id_usuario) {
        return r_locacao.getLocacoesEmCurso(id_usuario);
    }

    public static List<M_ViewLocacao> getLocacoesPassadas(Long id_usuario){
        return r_locacao.getLocacoesPassadas(id_usuario);
    }

    public static List<M_ViewLocacao> getLocacoesFuturas(Long id_usuario){
        return r_locacao.getLocacoesFuturas(id_usuario);
    }

}
