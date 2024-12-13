package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.model.M_ViewLocacao;
import com.primeira.appSpring.service.S_Home;
import com.primeira.appSpring.service.S_Login;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class C_Login {
    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario,
                            @RequestParam("senha") String senha,
                            HttpSession session) {
        M_Usuario m_usuario = S_Login.validaLogin(usuario, senha);
        session.setAttribute("usuario", m_usuario);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getLogin(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            long id = ((M_Usuario) session.getAttribute("usuario")).getId();
            List<M_ViewLocacao> ativas = S_Home.getLocacoesAtivas(id);
            List<M_ViewLocacao> passadas = S_Home.getLocacoesPassadas(id);
            List<M_ViewLocacao> futuras = S_Home.getLocacoesFuturas(id);

            if(ativas.isEmpty() && passadas.isEmpty() && futuras.isEmpty()){
                return "home/noLocacoes";
            }else {
                model.addAttribute("ativas", ativas);
                model.addAttribute("passadas", passadas);
                model.addAttribute("futuras", futuras);
                return "home/home";
            }
        }
        return "index";
    }

    @GetMapping("/logout")
    @ResponseBody
    public boolean getLogout(HttpSession session) {
        session.setAttribute("usuario", null);
        return true;
    }
}
