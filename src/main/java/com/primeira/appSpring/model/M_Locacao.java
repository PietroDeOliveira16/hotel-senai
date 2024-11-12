package com.primeira.appSpring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "locacao", schema = "hotel")
public class M_Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime check_in;
    private LocalDateTime check_out;
    private BigDecimal senha;
    private long id_quarto;
    private int num_quarto;
    private long id_usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCheck_in() {
        return check_in;
    }

    public void setCheck_in(LocalDateTime check_in) {
        this.check_in = check_in;
    }

    public LocalDateTime getCheck_out() {
        return check_out;
    }

    public void setCheck_out(LocalDateTime check_out) {
        this.check_out = check_out;
    }

    public BigDecimal getSenha() {
        return senha;
    }

    public void setSenha(BigDecimal senha) {
        this.senha = senha;
    }

    public long getId_quarto() {
        return id_quarto;
    }

    public void setId_quarto(long id_quarto) {
        this.id_quarto = id_quarto;
    }

    public int getNum_quarto() {
        return num_quarto;
    }

    public void setNum_quarto(int num_quarto) {
        this.num_quarto = num_quarto;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }
}
