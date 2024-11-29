package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class S_ValidaSenha {
    private static R_Locacao r_locacao;

    public S_ValidaSenha(R_Locacao r_locacao) {
        this.r_locacao = r_locacao;
    }

    public static boolean ValidarSenha(String senhaRecebida){
        BigDecimal bigDecimal = new BigDecimal(senhaRecebida);
        Optional<M_Locacao> m_locacao = r_locacao.findBySenha(bigDecimal);
        if(m_locacao.isPresent()){
            return true;
        }
        return false;
    }
}
