package br.com.crmbonus.clientes.service;

import java.math.BigDecimal;

public interface RequestHashService {

    String generateHash(BigDecimal nuNota);

}