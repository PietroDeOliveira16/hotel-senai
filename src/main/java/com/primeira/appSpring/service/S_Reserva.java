package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S_Reserva {
    @Autowired
    public R_Locacao r_locacao;

    public M_Locacao realizarReserva(String checkin, String checkout, int quarto, int usuario){
        return null;
    }
}
