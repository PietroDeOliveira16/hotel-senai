package com.primeira.appSpring.rest;

import com.primeira.appSpring.model.M_ApiJson;
import com.primeira.appSpring.service.S_Compra;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompraRestController {
    private final S_Compra s_compra;

    public CompraRestController(S_Compra s_compra) {
        this.s_compra = s_compra;
    }

    @GetMapping("/API/{dataIso}")
    public List<M_ApiJson> getProdutosData(@PathVariable("dataIso") String data){
        return s_compra.gerarResponse(data);
    }
}
