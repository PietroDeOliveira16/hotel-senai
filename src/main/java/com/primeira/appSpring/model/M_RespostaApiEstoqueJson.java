package com.primeira.appSpring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class M_RespostaApiEstoqueJson {
    @JsonProperty("nome_produto")
    private String nomeProduto;

    @JsonProperty("quantidade")
    private int quantidade;

    @JsonProperty("data_compra")
    private LocalDateTime dataCompra;

    @JsonProperty("cod_barras")
    private String codBarras;

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }
}
