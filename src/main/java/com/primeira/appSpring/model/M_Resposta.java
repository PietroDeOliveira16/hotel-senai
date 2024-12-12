package com.primeira.appSpring.model;

public class M_Resposta {
    private boolean sucesso;
    private String mensagem;
    private M_Locacao m_locacao;

    public M_Resposta() {
        this.sucesso = false;
        this.mensagem = "";
        this.m_locacao = new M_Locacao();
    }

    public M_Resposta(boolean sucesso, String mensagem, M_Locacao m_locacao){
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.m_locacao = m_locacao;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public M_Locacao getM_locacao() {
        return m_locacao;
    }

    public void setM_locacao(M_Locacao m_locacao) {
        this.m_locacao = m_locacao;
    }
}
