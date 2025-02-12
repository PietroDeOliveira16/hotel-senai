package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_ConsumoLocacao;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_ConsumoLocacao;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Produto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class S_Diarias {
    @Autowired
    private R_Locacao r_locacao;
    @Autowired
    private R_Produto r_produto;
    @Autowired
    private R_ConsumoLocacao r_consumoLocacao;

    @Scheduled(cron = "0 0 9 * * ?")
    public void gerarDiarias() {
        List<M_Locacao> locacoes = r_locacao.getLocacoesGerarDiarias();
        M_Produto diaria = r_produto.getReferenceById(9L);
        for (M_Locacao locacao : locacoes) {
            M_ConsumoLocacao m_consumoLocacao = new M_ConsumoLocacao();
            m_consumoLocacao.setQuantidade(1);
            m_consumoLocacao.setProduto(diaria);
            m_consumoLocacao.setPreco(locacao.getDiaria());
            m_consumoLocacao.setLocacao(locacao);
            m_consumoLocacao.setData(LocalDateTime.now());
            r_consumoLocacao.save(m_consumoLocacao);
        }
    }
}
