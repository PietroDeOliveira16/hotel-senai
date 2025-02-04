package com.primeira.appSpring.controller;

import com.primeira.appSpring.service.S_ValidaSenha;
import org.springframework.stereotype.Controller;

@Controller
public class C_ValidaSenha {
    private final S_ValidaSenha s_validaSenha;

    public C_ValidaSenha(S_ValidaSenha s_validaSenha) {
        this.s_validaSenha = s_validaSenha;
    }

    public String ChecarSenha(String senhaAtual) {
        if (!senhaAtual.isEmpty() && s_validaSenha.ValidarSenha(senhaAtual)) {
            return "true";
        }
        return "false";
    }
}
