package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Home;
import com.primeira.appSpring.service.S_Login;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
            model.addAttribute("locacoes", S_Home.getLocacoes(((M_Usuario) session.getAttribute("usuario")).getId()));
            return "home/home";
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
