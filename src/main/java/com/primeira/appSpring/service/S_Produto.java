package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Produto {
    @Autowired
    private R_Produto r_produto;

    public List<M_Produto> findAllProdutos(){
        return r_produto.findAllProdutos();
    }

    public M_Produto findByCode(String codigo){
        return r_produto.findByCodeBar(codigo);
    }
}
