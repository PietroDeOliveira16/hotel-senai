package com.primeira.appSpring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.primeira.appSpring.service.S_Home;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class C_Locacao {

    @GetMapping("/cadLocacao")
    public String getLocacao(HttpSession session, Model model) {
        if(session.getAttribute("usuario") != null){
            // Obter data e hora atuais
            LocalDateTime now = LocalDateTime.now();

            // Formatar a data no formato adequado para datetime-local
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String formattedDate = now.format(formatter);

            // Passar a data formatada para o modelo
            model.addAttribute("currentDate", formattedDate);
            model.addAttribute("quartos", S_Home.getQuartos());
            return "locacao/cadastro";
        }
        return "redirect:/";
    }
}
