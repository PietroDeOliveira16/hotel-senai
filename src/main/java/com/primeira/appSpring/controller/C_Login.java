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
    private final S_Home s_home;
    private final S_Login s_login;

    public C_Login(S_Home s_home, S_Login s_login) {
        this.s_home = s_home;
        this.s_login = s_login;
    }

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario,
                            @RequestParam("senha") String senha,
                            HttpSession session) {
        M_Usuario m_usuario = s_login.validaLogin(usuario, senha);
        session.setAttribute("usuario", m_usuario);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getLogin(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            long id = ((M_Usuario) session.getAttribute("usuario")).getId();
            List<M_ViewLocacao> ativas = s_home.getLocacoesAtivas(id);
            List<M_ViewLocacao> passadas = s_home.getLocacoesPassadas(id);
            List<M_ViewLocacao> futuras = s_home.getLocacoesFuturas(id);

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
