package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_ProdutoJson;
import com.primeira.appSpring.model.M_ViewCompra;
import com.primeira.appSpring.model.M_ViewVenda;
import com.primeira.appSpring.repository.R_Compra;
import com.primeira.appSpring.repository.R_ConsumoLocacao;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class S_Compra {
    @Autowired
    private R_Compra r_compra;
    @Autowired
    private R_ConsumoLocacao r_consumoLocacao;

    public M_ProdutoJson gerarResponse(String data){
        List<M_ViewCompra> compras = r_compra.findQuantidadeComprasData(data);
        List<M_ViewVenda> vendas = r_consumoLocacao.findVendaData(data);

        return null;
    }
}
