package com.primeira.appSpring.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Locacao;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.DateFormatter;

@Controller
public class C_Locacao {

    @GetMapping("/cadLocacao")
    public String getLocacao(HttpSession session, Model model) {
        LocalDate hoje = LocalDate.now();
        if (session.getAttribute("usuario") != null) {
            // Passar a data formatada para o modelo
            model.addAttribute("currentDate", hoje);
            model.addAttribute("tomorrowDate", hoje.plusDays(1));
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
                              @RequestParam("data_checkIn") LocalDate data_checkIn,
                              @RequestParam("data_checkOut") LocalDate data_checkOut) {

        LocalDateTime checkIn = data_checkIn.atTime(12, 0);
        LocalDateTime checkOut = data_checkOut.atTime(10, 0);
        LocalDate hoje = LocalDate.now();

        M_Usuario m_usuario = (M_Usuario) session.getAttribute("usuario");
        M_Quarto m_quarto = S_Locacao.acharQuarto(numero_quarto);
        model.addAttribute("currentDate", hoje);
        model.addAttribute("tomorrowDate", hoje.plusDays(1));
        if (m_usuario != null) {
            M_Locacao m_locacao = S_Locacao.realizarLocacao(numero_quarto, checkIn, checkOut,
                    m_usuario.getId(), m_quarto.getId());
            if (m_locacao != null) {
                model.addAttribute("num_quarto", m_locacao.getNum_quarto());
                model.addAttribute("email_user", ((M_Usuario) session.getAttribute("usuario")).getEmail());
                return "locacao/sucesso";
            } else {

                model.addAttribute("mensagemErro", "Erro ao locar o quarto");
                return "locacao/cadastro";
            }
        } else {
            model.addAttribute("currentDate", hoje);
            model.addAttribute("tomorrowDate", hoje.plusDays(1));
            //model.addAttribute("quartos", S_Locacao.getQuartosDisponiveis());
            model.addAttribute("mensagemErro", "Usuário não autenticado");
            return "locacao/cadastro";
        }
    }

    @PostMapping("/buscarQuartos")
    public String postBusca(HttpSession session, Model model,
                            @RequestParam("checkIn") String data_checkIn,
                            @RequestParam("checkOut") String data_checkOut){

        data_checkIn += " 12:00";
        data_checkOut += " 10:00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime checkIn = LocalDateTime.parse(data_checkIn, formatter);
        LocalDateTime checkOut = LocalDateTime.parse(data_checkOut, formatter);
        List<M_Quarto> quartos = S_Locacao.getQuartosDisponiveis(checkIn, checkOut);
        model.addAttribute("quartos", quartos);
        return "/locacao/pv/quarto";
    }
}
