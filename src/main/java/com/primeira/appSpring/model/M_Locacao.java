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
    private int senha;
    private int num_quarto;
    private BigDecimal diaria;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private M_Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_quarto", nullable = false)
    private M_Quarto quarto;

    @Column(name = "is_checked", nullable = false)
    private Boolean is_checked = Boolean.FALSE;

    @Column(name = "no_show", nullable = false)
    private Boolean no_show = Boolean.FALSE;

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

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public int getNum_quarto() {
        return num_quarto;
    }

    public void setNum_quarto(int num_quarto) {
        this.num_quarto = num_quarto;
    }

    public BigDecimal getDiaria() {
        return diaria;
    }

    public void setDiaria(BigDecimal diaria) {
        this.diaria = diaria;
    }

    public M_Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(M_Usuario usuario) {
        this.usuario = usuario;
    }

    public M_Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(M_Quarto quarto) {
        this.quarto = quarto;
    }

    public Boolean getChecked() {
        return is_checked;
    }

    public void setChecked(Boolean checked) {
        is_checked = checked;
    }

    public Boolean getNo_show() {
        return no_show;
    }

    public void setNo_show(Boolean no_show) {
        this.no_show = no_show;
    }
}
