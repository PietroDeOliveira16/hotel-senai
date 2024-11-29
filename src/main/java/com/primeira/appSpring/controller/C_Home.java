package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class C_Home {
    @PostMapping("/addLocacoes")
    public String postHome(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            List<M_Locacao> locacoes = S_Home.getLocacoes(((M_Usuario) session.getAttribute("usuario")).getId());
            if (locacoes.isEmpty()) {
                return "home/pv/noLocacoes";
            } else {
                model.addAttribute("locacoes", locacoes);
                model.addAttribute("responsa", ((M_Usuario) session.getAttribute("usuario")).getUsuario());
                return "home/pv/locacoes";
            }
        }
        return "redirect:/";
    }


}
