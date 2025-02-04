package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class S_ValidaSenha {
    @Autowired
    private R_Locacao r_locacao;

    public boolean ValidarSenha(String senhaRecebida) {
        int senha = Integer.parseInt(senhaRecebida);
        Optional<M_Locacao> m_locacao = r_locacao.findIfSenhaIsCorrect(senha);
        if (m_locacao.isPresent()) {
            return true;
        }
        return false;
    }
}
