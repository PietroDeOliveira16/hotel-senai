package com.primeira.appSpring.model;

import java.math.BigDecimal;

public class M_ProdutoJson {
    private String produto;
    private int quantidade;
    private int min;
    private int max;
    private BigDecimal custo_medio;
    private String ultima_compra;

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public BigDecimal getCusto_medio() {
        return custo_medio;
    }

    public void setCusto_medio(BigDecimal custo_medio) {
        this.custo_medio = custo_medio;
    }

    public String getUltima_compra() {
        return ultima_compra;
    }

    public void setUltima_compra(String ultima_compra) {
        this.ultima_compra = ultima_compra;
    }
}
