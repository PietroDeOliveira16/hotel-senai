package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Cadastro {
    @Autowired
    private R_Usuario r_usuario;

    public M_Usuario cadastrarUsuario(String usuario, String apelido, String senha, String conf_senha,
                                             String telefone, String cpf, String email) {
        boolean podeSalvar = true;

        if (senha == null || !senha.trim().equals(conf_senha)) {
            podeSalvar = false;
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            podeSalvar = false;
        }
        if (apelido == null || apelido.trim().isEmpty()) {
            podeSalvar = false;
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            podeSalvar = false;
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            podeSalvar = false;
        }
        if (email == null || email.trim().isEmpty()) {
            podeSalvar = false;
        }

        if (podeSalvar) {
            M_Usuario m_usuario = new M_Usuario();
            m_usuario.setUsuario(usuario);
            m_usuario.setApelido(apelido);
            m_usuario.setSenha(senha);
            m_usuario.setTelefone(telefone);
            m_usuario.setCpf(cpf);
            m_usuario.setEmail(email);
            return r_usuario.save(m_usuario);
        }
        return null;
    }
}
