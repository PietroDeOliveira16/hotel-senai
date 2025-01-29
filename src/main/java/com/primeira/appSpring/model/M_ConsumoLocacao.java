package com.primeira.appSpring.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumo_locacao", schema = "hotel")
public class M_ConsumoLocacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_produto", nullable = false)
    private M_Produto produto;
    @ManyToOne
    @JoinColumn(name = "id_locacao", nullable = false)
    private M_Locacao locacao;
    private int quantidade;
    private BigDecimal preco;
    private LocalDateTime data_consumo;
}
