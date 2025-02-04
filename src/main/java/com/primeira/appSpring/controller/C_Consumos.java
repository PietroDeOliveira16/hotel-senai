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

@Controller
public class C_Consumos {

    private final S_Consumo s_consumo;
    private final S_Locacao s_locacao;
    private final S_Produto s_produto;

    public C_Consumos(S_Consumo s_consumo, S_Produto s_produto, S_Locacao s_locacao) {
        this.s_consumo = s_consumo;
        this.s_produto = s_produto;
        this.s_locacao = s_locacao;
    }

    @GetMapping("/consumo/{id}")
    public String getConsumos(HttpSession session,
                              Model model,
                              @PathVariable("id") long id) throws JsonProcessingException {

        if (session.getAttribute("usuario") != null) {
            List<M_Produto> listaProdutos = s_produto.findAllProdutos();
            String produtosJson = new ObjectMapper().writeValueAsString(listaProdutos);

            model.addAttribute("jsonProdutos", produtosJson);
            model.addAttribute("listaProdutos", listaProdutos);
            model.addAttribute("listaConsumos", s_consumo.getListaConsumosLocacao(id));
            model.addAttribute("locacaoAtual", s_locacao.getLocacaoId(id).orElse(null));
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
        M_ConsumoLocacao m_consumoLocacao = s_consumo.registrarConsumo(id_produto, id_locacao, quantidade, preco);
        if (m_consumoLocacao != null) {
            model.addAttribute("consumoItem", m_consumoLocacao);
            return "consumos/pv/item";
        } else {
            return "index";
        }
    }
}