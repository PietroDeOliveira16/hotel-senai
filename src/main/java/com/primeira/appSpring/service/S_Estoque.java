package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Estoque;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.model.M_RespostaApiEstoqueJson;
import com.primeira.appSpring.repository.R_Estoque;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class S_Estoque {
    @Autowired
    private R_Estoque r_estoque;
    @Autowired
    private R_Produto r_produto;

    public List<M_Estoque> recarregarEstoqueApi(Long idHotel){
        RestTemplate rt = new RestTemplate();
        List<M_Estoque> resposta = new ArrayList<>();
        ResponseEntity<List<M_RespostaApiEstoqueJson>> response = rt.exchange(
                "http://localhost:8081/minhasComprasRecebidas/" + idHotel,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<M_RespostaApiEstoqueJson>>() {}
        );

            for(M_RespostaApiEstoqueJson compraJson : Objects.requireNonNull(response.getBody())){
                M_Estoque m_estoque = new M_Estoque();
                m_estoque.setNomeProduto(compraJson.getNomeProduto());
                m_estoque.setQuantidade(compraJson.getQuantidade());
                m_estoque.setDataCompra(compraJson.getDataCompra());
                m_estoque.setCodBarras(compraJson.getCodBarras());
                resposta.add(m_estoque);
            }


        return r_estoque.saveAll(resposta);
    }

    public void addCompraToEstoque(){

    }
}
