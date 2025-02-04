package com.primeira.appSpring.service;

import com.primeira.appSpring.model.*;
import com.primeira.appSpring.repository.R_ConsumoLocacao;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class S_Consumo {
    @Autowired
    private R_ConsumoLocacao r_consumo;
    @Autowired
    private R_Produto r_produto;
    @Autowired
    private R_Locacao r_locacao;

    public M_ConsumoLocacao registrarConsumo(long id_produto, long id_locacao, int quantidade, BigDecimal preco) {
        boolean podeRegistrar = true;

        if (id_produto < 1) {
            podeRegistrar = false;
        }

        if (id_locacao < 1) {
            podeRegistrar = false;
        }

        if (quantidade < 1) {
            quantidade = 1;
        }

        if (preco.toString().isEmpty()) {
            podeRegistrar = false;
        }

        if (podeRegistrar) {
            LocalDateTime todayTime = LocalDateTime.now();
            Optional<M_Produto> m_produto = r_produto.findById(id_produto);
            Optional<M_Locacao> m_locacao = r_locacao.findById(id_locacao);

            M_ConsumoLocacao m_consumoLocacao = new M_ConsumoLocacao();
            m_consumoLocacao.setProduto(m_produto.orElse(null));
            m_consumoLocacao.setLocacao(m_locacao.orElse(null));
            m_consumoLocacao.setPreco(preco);
            m_consumoLocacao.setQuantidade(quantidade);
            m_consumoLocacao.setData(todayTime);
            return r_consumo.save(m_consumoLocacao);
        } else {
            return null;
        }
    }

    public List<M_ConsumoLocacao> getListaConsumosLocacao(long idLocacao) {
        return r_consumo.findConsumoLocacao(idLocacao);
    }
}
