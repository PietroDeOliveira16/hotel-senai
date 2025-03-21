package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Estoque;
import com.primeira.appSpring.service.S_Estoque;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class C_Estoque {
    private final S_Estoque s_estoque;

    public C_Estoque(S_Estoque s_estoque) {
        this.s_estoque = s_estoque;
    }

    @GetMapping("/visualizarEstoque")
    public String getEstoque(HttpSession session, Model model){
        if(session.getAttribute("usuario") != null){
            List<M_Estoque> estoque = s_estoque.recarregarEstoqueApi(2L);
            model.addAttribute("estoque", estoque != null ? estoque : new M_Estoque());
            return "/estoque/estoque";
        } else {
            return "index";
        }
    }

    @PostMapping("/receberEstoque")
    public String postEstoque(Model model){
        List<M_Estoque> estoque = s_estoque.recarregarEstoqueApi(2L);
        model.addAttribute("estoque", estoque != null ? estoque : new M_Estoque());
        return "/estoque/pv/comprasEstoque";
    }
}
