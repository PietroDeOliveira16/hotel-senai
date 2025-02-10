package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Resposta;
import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Quarto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class S_Locacao {
    @Autowired
    private R_Quarto r_quarto;
    @Autowired
    private R_Locacao r_locacao;

    public M_Quarto acharQuarto(int numero) {
        return r_quarto.findByNumero(numero);
    }

    public M_Resposta realizarLocacao(int numero_quarto, LocalDateTime data_checkIn,
                                             LocalDateTime data_checkOut, M_Usuario usuario, M_Quarto quarto) {
        M_Resposta m_resposta = new M_Resposta();

        boolean podeLocar = true;

        if (numero_quarto <= 0) {
            podeLocar = false;
            m_resposta.setMensagem("Número de quarto inexistente no hotel.");
        }
        if (data_checkIn == null || data_checkIn.isBefore(LocalDate.now().atStartOfDay())) {
            podeLocar = false;
            m_resposta.setMensagem("Data de check-in inválida, a data de check-in deve ser a partir do dia de hoje.");
        }
        if (data_checkOut == null || data_checkOut.isBefore(data_checkIn)) {
            podeLocar = false;
            m_resposta.setMensagem("Data de check-out inválida, a data de check-out deve ser após a data de check-in.");
        }
        if (usuario == null) {
            podeLocar = false;
            m_resposta.setMensagem("Nenhum usuário foi encontrado nesta sessão, por favor entre na sua conta.");
        }
        M_Locacao m_locacaoLocada = r_locacao.quartoEstaLocado(quarto.getId(), data_checkIn, data_checkOut);
        if (m_locacaoLocada != null) {
            podeLocar = false;
            m_resposta.setMensagem("Já existe uma locação na data inserida, por favor selecione datas diferentes.");
        }

        M_Quarto m_quarto = r_quarto.findById(quarto.getId()).orElse(null);
        if (m_quarto == null) {
            podeLocar = false;
            m_resposta.setMensagem("Quarto selecionado já está locado, por favor selecione outro quarto.");
        }

        m_resposta.setSucesso(podeLocar);

        if (podeLocar) {
            try {
                M_Locacao m_locacao = new M_Locacao();
                m_locacao.setUsuario(usuario);
                m_locacao.setNum_quarto(numero_quarto);
                m_locacao.setCheck_in(data_checkIn);
                m_locacao.setCheck_out(data_checkOut);

                Random random = new Random(System.nanoTime());
                int senha = random.nextInt(1000, 10000);

                try {
                    while (isSenhaRepetida(senha, data_checkIn, data_checkOut)) {
                        senha = random.nextInt(1000, 10000);
                    }
                } catch (Exception e) {
                    m_resposta.setMensagem("Erro interno do banco de dados, por favor tente novamente mais tarde.");
                }

                m_locacao.setSenha(senha);
                m_locacao.setQuarto(quarto);

                // Preço quarto normal = R$60,00
                // Preço quarto luxo = R$179,90
                m_locacao.setDiaria(r_quarto.findByNumero(numero_quarto).getPreco());

                r_locacao.save(m_locacao);

                // if(data_checkIn.equals(LocalDateTime.now()) && LocalDateTime.now().getHour() > 12)
                m_resposta.setM_locacao(m_locacao);
                return m_resposta;
            } catch (Exception e) {
                m_resposta.setMensagem("Erro interno do servidor, por favor tente novamente mais tarde.");
            }
        }

        return m_resposta;
    }

    private boolean isSenhaRepetida(int senha, LocalDateTime check_in, LocalDateTime check_out) {
        return r_locacao.findIfSenhaRepetida(senha, check_in, check_out).isPresent(); // verifica se o objeto retornado possui campos preenchidos (a senha ja
        // existe em algum lugar) ou se o objeto esta null (a senha n existe)
    }

    public List<M_Quarto> getQuartosDisponiveis(LocalDateTime dataCheckIn, LocalDateTime dataCheckOut) {
        return r_quarto.findQuartosDisponiveis(dataCheckIn.withHour(12), dataCheckOut.withHour(10));
    }

    public Optional<M_Locacao> getLocacaoId(long id){
        return r_locacao.findById(id);
    }
}
