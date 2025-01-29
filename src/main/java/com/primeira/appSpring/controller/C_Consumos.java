package com.primeira.appSpring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Locacao;
import com.primeira.appSpring.service.S_Produto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class C_Consumos {
    @GetMapping("/consumo")
    public String getConsumos(Model model, @RequestParam("id") Long id) throws JsonProcessingException {
        List<M_Produto> listaProdutos = S_Produto.findAllProdutos();
        String produtosJson = new ObjectMapper().writeValueAsString(listaProdutos);
        model.addAttribute("jsonProdutos", produtosJson);
        model.addAttribute("listaProdutos", listaProdutos);
        model.addAttribute("idLocacaoAtual", id);
        return "consumos/consumo";
    }

    @PostMapping("/item-consumo")
    public String postItemConsumo(){
        return "consumos/item";
    }

    @PostMapping("/registrarConsumo")
    public String postRegistroConsumo(HttpSession session){
        M_Usuario usuario = (M_Usuario) session.getAttribute("usuario");
        return null;
    }
}