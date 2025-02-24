package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_NoShow {
    @Autowired
    private R_Locacao r_locacao;
    @Autowired
    private S_Email s_email;

    @Scheduled(cron = "0 0 9 * * ?")
    public void gerarNoShow(){
        List<M_Locacao> locacoes = r_locacao.getLocacaoesGerarNoShow();
        for(M_Locacao locacao : locacoes){
            r_locacao.updateNoShowLocacao(locacao.getId());
            s_email.enviarEmailLocacoesNoShow(locacao);
        }
    }
}
