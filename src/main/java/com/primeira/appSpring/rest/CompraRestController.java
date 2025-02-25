package com.primeira.appSpring.rest;

import com.primeira.appSpring.model.M_ProdutoJson;
import com.primeira.appSpring.service.S_Compra;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CompraRestController {
    private final S_Compra s_compra;

    public CompraRestController(S_Compra s_compra) {
        this.s_compra = s_compra;
    }

    @GetMapping("/API/{dataIso}")
    public M_ProdutoJson getProdutosData(@PathVariable("dataIso") String data){
        M_ProdutoJson response = s_compra.gerarResponse(data);

        return null;
    }
}
