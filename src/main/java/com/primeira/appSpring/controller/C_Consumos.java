package com.primeira.appSpring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeira.appSpring.model.*;
import com.primeira.appSpring.service.S_Consumo;
import com.primeira.appSpring.service.S_Locacao;
import com.primeira.appSpring.service.S_Produto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class C_Consumos {
    @GetMapping("/consumo/{id}")
    public String getConsumos(HttpSession session, Model model, @PathVariable("id") long id) throws JsonProcessingException {
        if (session.getAttribute("usuario") != null) {
            List<M_Produto> listaProdutos = S_Produto.findAllProdutos();
            String produtosJson = new ObjectMapper().writeValueAsString(listaProdutos);

            List<M_ConsumoLocacao> m_consumoLocacao = S_Consumo.getListaConsumosLocacao(id);

            if (m_consumoLocacao != null) {
                model.addAttribute("jsonProdutos", produtosJson);
                model.addAttribute("listaProdutos", listaProdutos);
                model.addAttribute("listaConsumos", m_consumoLocacao);
                model.addAttribute("locacaoAtual", S_Locacao.getLocacaoId(id).orElse(null));
            }
            return "consumos/consumo";
        } else {
            return "index";
        }
    }

    @PostMapping("/registrarConsumo")
    public String postRegistroConsumo(@RequestParam("id_produto") long id_produto,
                                       @RequestParam("id_locacao") long id_locacao,
                                       @RequestParam("quantidade") int quantidade,
                                       @RequestParam("preco") BigDecimal preco,
                                      Model model) {
        M_ConsumoLocacao m_consumoLocacao = S_Consumo.registrarConsumo(id_produto, id_locacao, quantidade, preco);
        if (m_consumoLocacao != null) {
            model.addAttribute("consumoItem", m_consumoLocacao);
            return "consumos/pv/item";
        } else {
            return "index";
        }
    }
}