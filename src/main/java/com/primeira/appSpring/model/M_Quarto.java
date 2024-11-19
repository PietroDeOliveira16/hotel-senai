package com.primeira.appSpring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "quartos", schema = "hotel")
public class M_Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int numero_quarto;
    private String tipo;
    private int espaco_pessoas;
    private int camas_solteiro;
    private int camas_casal;
    private int banheiros;
    private boolean closet;
    private BigDecimal preco;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumero_quarto() {
        return numero_quarto;
    }

    public void setNumero_quarto(int numero_quarto) {
        this.numero_quarto = numero_quarto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEspaco_pessoas() {
        return espaco_pessoas;
    }

    public void setEspaco_pessoas(int espaco_pessoas) {
        this.espaco_pessoas = espaco_pessoas;
    }

    public int getCamas_solteiro() {
        return camas_solteiro;
    }

    public void setCamas_solteiro(int camas_solteiro) {
        this.camas_solteiro = camas_solteiro;
    }

    public int getCamas_casal() {
        return camas_casal;
    }

    public void setCamas_casal(int camas_casal) {
        this.camas_casal = camas_casal;
    }

    public int getBanheiros() {
        return banheiros;
    }

    public void setBanheiros(int banheiros) {
        this.banheiros = banheiros;
    }

    public boolean isCloset() {
        return closet;
    }

    public void setCloset(boolean closet) {
        this.closet = closet;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
