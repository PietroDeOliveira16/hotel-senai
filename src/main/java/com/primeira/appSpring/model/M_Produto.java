package com.primeira.appSpring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto", schema = "hotel")
public class M_Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cod_barras;
    private String nome;
    private BigDecimal preco;

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
