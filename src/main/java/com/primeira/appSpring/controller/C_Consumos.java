package com.primeira.appSpring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeira.appSpring.model.M_ConsumoLocacao;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Consumo;
import com.primeira.appSpring.service.S_Locacao;
import com.primeira.appSpring.service.S_Produto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class C_Consumos {
    @GetMapping("/consumo")
    public String getConsumos(HttpSession session,Model model, @RequestParam("idLocacao") long id) throws JsonProcessingException {
        if(session.getAttribute("usuario") != null) {
            List<M_Produto> listaProdutos = S_Produto.findAllProdutos();
            String produtosJson = new ObjectMapper().writeValueAsString(listaProdutos);
            model.addAttribute("jsonProdutos", produtosJson);
            model.addAttribute("listaProdutos", listaProdutos);
            model.addAttribute("idLocacaoAtual", id);
            return "consumos/consumo";
        }else{
            return "index";
        }
    }

    @PostMapping("/item-consumo")
    public String postItemConsumo(){
        return "consumos/item";
    }

    @PostMapping("/registrarConsumo")
    @ResponseBody
    public boolean postRegistroConsumo(@RequestParam("id_produto") long id_produto,
                                      @RequestParam("id_locacao") long id_locacao,
                                      @RequestParam("quantidade") int quantidade,
                                      @RequestParam("preco")BigDecimal preco){
        M_ConsumoLocacao m_consumoLocacao = S_Consumo.registrarConsumo(id_produto, id_locacao, quantidade, preco);
        if(m_consumoLocacao != null){
            return true;
        }else{
            return false;
        }
    }
}