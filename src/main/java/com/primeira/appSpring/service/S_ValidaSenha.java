package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class S_ValidaSenha {
    @Autowired
    private R_Locacao r_locacao;

    public String ValidarSenha(String senhaRecebida) {
        int senha = 0;
        try{
            senha = Integer.parseInt(senhaRecebida);
        }catch (Exception e){
            senha = 0;
        }
        Optional<M_Locacao> m_locacao = r_locacao.findIfSenhaIsCorrect(senha);
        if (m_locacao.isPresent()) {
            if(!m_locacao.get().getChecked()){
                r_locacao.updateIsCheckedLocacao(m_locacao.get().getId());
            }
            return "true";
        }
        return "false";
    }
}
