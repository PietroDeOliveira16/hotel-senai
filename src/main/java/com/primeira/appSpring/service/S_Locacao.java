package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Quarto;
import com.primeira.appSpring.model.M_Resposta;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Quarto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class S_Locacao {
    private static R_Quarto r_quarto;
    private static R_Locacao r_locacao;

    public S_Locacao(R_Quarto r_quarto, R_Locacao r_locacao) {
        this.r_quarto = r_quarto;
        this.r_locacao = r_locacao;
    }

    public static M_Quarto acharQuarto(int numero) {
        return r_quarto.findByNumero(numero);
    }

    public static M_Resposta realizarLocacao(int numero_quarto, LocalDateTime data_checkIn,
                                             LocalDateTime data_checkOut, long id_usuario, long id_quarto, long dias) {
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
        if (id_usuario <= 0) {
            podeLocar = false;
            m_resposta.setMensagem("Nenhum usuário foi encontrado nesta sessão, por favor entre na sua conta.");
        }
        M_Locacao m_locacaoLocada = r_locacao.quartoEstaLocado(id_quarto, data_checkIn, data_checkOut);
        if (m_locacaoLocada != null) {
            podeLocar = false;
            m_resposta.setMensagem("Já existe uma locação na data inserida, por favor selecione datas diferentes.");
        }

        M_Quarto m_quarto = r_quarto.findById(id_quarto).orElse(null);
        if (m_quarto == null) {
            podeLocar = false;
            m_resposta.setMensagem("Quarto selecionado já está locado, por favor selecione outro quarto.");
        }

        m_resposta.setSucesso(podeLocar);

        if (podeLocar) {
            try {
                M_Locacao m_locacao = new M_Locacao();
                m_locacao.setId_usuario(id_usuario);
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
                m_locacao.setId_quarto(id_quarto);

                // Preço quarto normal = R$60,00
                // Preço quarto luxo = R$179,90
                m_locacao.setDiaria(r_quarto.findByNumero(numero_quarto).getPreco());

                m_locacao.setDias_estadia(dias);

                BigDecimal qtdDias = BigDecimal.valueOf(dias);

                m_locacao.setPreco_total(qtdDias.multiply(m_locacao.getDiaria()));

                r_locacao.save(m_locacao);
                m_resposta.setM_locacao(m_locacao);
                return m_resposta;
            } catch (Exception e) {
                m_resposta.setMensagem("Erro interno do servidor, por favor tente novamente mais tarde.");
            }
        }

        return m_resposta;
    }

    private static boolean isSenhaRepetida(int senha, LocalDateTime check_in, LocalDateTime check_out) {
        return r_locacao.findIfSenhaRepetida(senha, check_in, check_out).isPresent(); // verifica se o objeto retornado possui campos preenchidos (a senha ja
        // existe em algum lugar) ou se o objeto esta null (a senha n existe)
    }

    public static List<M_Quarto> getQuartosDisponiveis(LocalDateTime dataCheckIn, LocalDateTime dataCheckOut) {
        return r_quarto.findQuartosDisponiveis(dataCheckIn, dataCheckOut);
    }
}
