package com.primeira.appSpring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Locacao;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class C_Locacao {

    @GetMapping("/cadLocacao")
    public String getLocacao(HttpSession session, Model model) {
        if (session.getAttribute("usuario") != null) {
            // Passar a data formatada para o modelo
            model.addAttribute("currentDate", formattedDateNow());
            model.addAttribute("quartos", S_Locacao.getQuartosDisponiveis());
            return "locacao/cadastro";
        }
        return "redirect:/";
    }

    @GetMapping("/quarto/{numero}")
    @ResponseBody
    public M_Quarto getQuartoInfo(@PathVariable int numero) {
        M_Quarto quarto = S_Locacao.acharQuarto(numero); // Obtém o quarto com o número passado na URL
        return quarto; // Retorna o objeto Quarto em formato JSON
    }

    @PostMapping("/locar")
    public String postLocacao(HttpSession session, Model model,
                              @RequestParam("numero_quarto") int numero_quarto,
                              @RequestParam("data_checkIn") LocalDateTime data_checkIn,
                              @RequestParam(value = "data_checkOut", required = false) LocalDateTime data_checkOut) {

        M_Usuario m_usuario = (M_Usuario) session.getAttribute("usuario");
        M_Quarto m_quarto = S_Locacao.acharQuarto(numero_quarto);
        model.addAttribute("currentDate", formattedDateNow());
        model.addAttribute("quartos", S_Locacao.getQuartosDisponiveis());
        if (m_usuario != null) {
            M_Locacao m_locacao = S_Locacao.realizarLocacao(numero_quarto, data_checkIn, data_checkOut,
                    m_usuario.getId(), m_quarto.getId());
            if (m_locacao != null) {
                model.addAttribute("nmr_quarto", m_locacao.getNum_quarto());
                model.addAttribute("email_user", ((M_Usuario) session.getAttribute("usuario")).getEmail());
                return "locacao/sucesso";
            } else {

                model.addAttribute("mensagemErro", "Erro ao locar o quarto");
                return "locacao/cadastro";
            }
        } else {
            model.addAttribute("currentDate", formattedDateNow());
            model.addAttribute("quartos", S_Locacao.getQuartosDisponiveis());
            model.addAttribute("mensagemErro", "Usuário não autenticado");
            return "locacao/cadastro";
        }
    }

    private static String formattedDateNow() {
        LocalDateTime now = LocalDateTime.now();

        // Formatar a data no formato adequado para datetime-local
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDate = now.format(formatter);

        return formattedDate;
    }
}
