package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.model.M_ViewLocacao;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Home {
    @Autowired
    private R_Usuario r_usuario;
    @Autowired
    private R_Locacao r_locacao;

    public List<M_ViewLocacao> getLocacoesAtivas(Long id_usuario) {
        return r_locacao.getLocacoesEmCurso(id_usuario);
    }

    public List<M_ViewLocacao> getLocacoesPassadas(Long id_usuario){
        return r_locacao.getLocacoesPassadas(id_usuario);
    }

    public List<M_ViewLocacao> getLocacoesFuturas(Long id_usuario){
        return r_locacao.getLocacoesFuturas(id_usuario);
    }

}
