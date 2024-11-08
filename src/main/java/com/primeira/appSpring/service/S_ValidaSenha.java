package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_ValidaSenha;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class S_ValidaSenha {
    private static R_ValidaSenha r_validaSenha;

    public S_ValidaSenha(R_ValidaSenha r_validaSenha) {
        this.r_validaSenha = r_validaSenha;
    }
    public static boolean ValidaSenha(String senhaRecebida){
        BigDecimal bigDecimal = new BigDecimal(senhaRecebida);
        M_Locacao m_locacao = r_validaSenha.getValidacaoBySenha(bigDecimal);
        if(m_locacao != null){
            return true;
        }
        return false;
    }
}
