package com.primeira.appSpring.controller;

import com.primeira.appSpring.service.S_ValidaSenha;
import org.springframework.stereotype.Controller;

@Controller
public class C_ValidaSenha {
    public static String ChecarSenha(String senhaAtual) {
        if (!senhaAtual.isEmpty() && S_ValidaSenha.ValidarSenha(senhaAtual)) {
            return "true";
        }
        return "false";
    }
}
