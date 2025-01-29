package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Produto {
    private static R_Produto r_produto;

    public S_Produto(R_Produto r_produto){
        this.r_produto = r_produto;
    }

    public static List<M_Produto> findAllProdutos(){
        return r_produto.findAll();
    }
}
