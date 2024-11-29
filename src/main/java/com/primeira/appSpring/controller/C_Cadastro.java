package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Cadastro;
import com.primeira.appSpring.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class C_Cadastro {

    //CADASTRO
    @GetMapping("/cadastro")
    public String getCadastro() {
        return "cadastro/cadastro";
    }

    @PostMapping("/cadastro")
    public String postCadastro(@RequestParam("usuario") String usuario,
                               @RequestParam("apelido") String apelido,
                               @RequestParam("senha") String senha,
                               @RequestParam("conf_senha") String conf_senha,
                               @RequestParam("telefone") String telefone,
                               @RequestParam("cpf") String cpf,
                               @RequestParam("email") String email) {
        M_Usuario m_usuario = S_Cadastro.cadastrarUsuario(usuario, apelido, senha, conf_senha, telefone, cpf, email);
        if (m_usuario != null) {
            return "index";
        }
        return "cadastro/cadastro";
    }
}
