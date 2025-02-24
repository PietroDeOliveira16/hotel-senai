package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.model.M_ViewLocacao;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class S_Email {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private R_Locacao r_locacao;

    @Autowired
    private R_Usuario r_usuario;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeFormatter dtf_timestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Scheduled(cron = "0 0 10 * * *")
    public void enviarEmailLocacoesFuturas(){
        List<M_Locacao> locacoes_amanha = r_locacao.findLocacoesAmanha();
        SimpleMailMessage email = new SimpleMailMessage();
        for (M_Locacao locacao : locacoes_amanha) {
            M_Usuario usuarioLocacao = locacao.getUsuario();
            long dias_estadia = ChronoUnit.DAYS.between(locacao.getCheck_out(), locacao.getCheck_in()) <= 0 ? 1 : ChronoUnit.DAYS.between(locacao.getCheck_out(), locacao.getCheck_in());
            BigDecimal total_locacao = BigDecimal.valueOf(dias_estadia).multiply(locacao.getDiaria());
            email.setTo(usuarioLocacao.getEmail());
            email.setSubject(""+usuarioLocacao.getApelido()+", suas informações sobre a locação do dia "+locacao.getCheck_in().toLocalDate().format(dtf));
            email.setText(""+ usuarioLocacao.getUsuario() + ", aqui estão as suas informações referentes a sua locação agendada. \n" +
                    "Número do quarto: " + locacao.getQuarto().getNumero_quarto() + "\n" +
                    "Senha do quarto: " + locacao.getSenha() + "\n" +
                    "Data de Check-In: " + locacao.getCheck_in().format(dtf_timestamp) + "\n" +
                    "Data de Check-Out: " + locacao.getCheck_out().format(dtf_timestamp) + "\n" +
                    "Dias de estadia reservados: " + dias_estadia + "\n" +
                    "Preço por dia (diária): R$" + locacao.getDiaria() + "\n" +
                    "Preço total estimado: R$" + total_locacao);

            mailSender.send(email);
        }
    }

    public void enviarEmailLocacoesNoShow(M_Locacao locacao){
        SimpleMailMessage email = new SimpleMailMessage();
        M_Usuario usuarioLocacao = locacao.getUsuario();
        email.setTo(usuarioLocacao.getEmail());
        email.setSubject(""+usuarioLocacao.getApelido()+", aviso de ausência locação Quarto " + locacao.getQuarto().getNumero_quarto());
        email.setText(""+ usuarioLocacao.getUsuario() +", não foi realizado o check_in da sua locação para o quarto " + locacao.getQuarto().getNumero_quarto() + ", referente ao dia "
                + locacao.getCheck_in().toLocalDate().format(dtf) + ". Lamentamos que isso tenha acontecido e se puder, nos informe o que aconteceu em nosso email hotelsenaifaq@gmail.com");
        mailSender.send(email);
    }

    @Scheduled(cron = "0 30 10 * * *")
    public void enviarEmailFicha(){
        List<M_ViewLocacao> locacoes_realizadas_sucesso = r_locacao.findLocacoesRealizadasSucesso();
        SimpleMailMessage email = new SimpleMailMessage();
        for (M_ViewLocacao locacao : locacoes_realizadas_sucesso) {
            M_Usuario usuarioLocacao = r_usuario.findUsuarioById(locacao.getIdUsuario());
            BigDecimal total_diarias = BigDecimal.valueOf(locacao.getDiasEstadia()).multiply(locacao.getDiaria());
            email.setTo(usuarioLocacao.getEmail());
            email.setSubject("Obrigado por se hospedar conosco, " + usuarioLocacao.getApelido());
            email.setText("O Hotel Senai agradece sua estadia! Aqui está uma ficha da sua locação para o quarto " + locacao.getNumeroQuarto() + ": \n" +
                    "Titular da locação: " + usuarioLocacao.getUsuario() + "\n" +
                    "Número do Quarto: " + locacao.getNumeroQuarto() + "\n" +
                    "Data de Check-In: " + locacao.getCheckIn().format(dtf_timestamp) + "\n" +
                    "Data de Check-Out: " + locacao.getCheckOut().format(dtf_timestamp) + "\n" +
                    "Dias de Estadia: " + locacao.getDiasEstadia() + "\n" +
                    "Preço por Dia (Diária): " + locacao.getDiaria() + "\n" +
                    "Total pago com as diárias: R$" + total_diarias + "\n" +
                    "Total pago nos consumos do quarto: R$" + locacao.getTotalConsumo() + "\n" +
                    "-------------------------------------------------------------------------\n" +
                    "Total da sua estadia: R$" + (BigDecimal.valueOf(locacao.getTotalConsumo()).add(total_diarias)));

            r_locacao.updateRecebeuFichaLocacao(locacao.getId());
            mailSender.send(email);
        }
    }
}
