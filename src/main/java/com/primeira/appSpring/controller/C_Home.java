package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class C_Home {
    @PostMapping("/addLocacoesAtivas")
    public String postActiveLocacoes(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            List<M_Locacao> locacoes = S_Home.getLocacoesAtivas(((M_Usuario) session.getAttribute("usuario")).getId());
            if (locacoes.isEmpty()) {
                return "home/pv/noLocacoes";
            } else {
                model.addAttribute("locacoes", locacoes);
                return "home/pv/locacoes";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/addLocacoesPassadas")
    public String postPastLocacoes(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            List<M_Locacao> locacoes = S_Home.getLocacoesPassadas(((M_Usuario) session.getAttribute("usuario")).getId());
            model.addAttribute("locacoes", locacoes);
            return "home/pv/locacoes";
        }
        return "redirect:/";
    }

    @PostMapping("/addLocacoesReservadas")
    public String postReservas(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            List<M_Locacao> locacoes = S_Home.getReservas(((M_Usuario) session.getAttribute("usuario")).getId());
            model.addAttribute("locacoes", locacoes);
            return "home/pv/locacoes";
        }
        return "redirect:/";
    }
}
