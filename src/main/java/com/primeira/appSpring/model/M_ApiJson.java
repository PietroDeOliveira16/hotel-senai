package com.primeira.appSpring.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface M_ApiJson {
    String getProduto();
    Long getQuantidade();
    int getMin();
    int getMax();
    BigDecimal getCusto_medio();
    LocalDate getUltima_compra();
}
