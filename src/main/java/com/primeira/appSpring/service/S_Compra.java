package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_ApiJson;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class S_Compra {
    @Autowired
    private R_Produto r_produto;

    public List<M_ApiJson> gerarResponse(String data){
        LocalDate date = LocalDate.parse(data);
        List<M_ApiJson> estoqueDoDia = r_produto.findEstoqueOfData(date);

        return estoqueDoDia;
    }
}
