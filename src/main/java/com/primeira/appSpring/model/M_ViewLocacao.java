package com.primeira.appSpring.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface M_ViewLocacao {
    long getId();
    String getNumeroQuarto();
    BigDecimal getDiaria();
    Integer getSenha();
    LocalDateTime getCheckIn();
    LocalDateTime getCheckOut();
    Integer getDiasEstadia();
    Double getTotalConsumo();
    Boolean getNoShow();
    Boolean getIsChecked();
}
