package br.com.zaya.nac.planocontas.repository;

import br.com.zaya.nac.planocontas.model.ContaCriada;
import br.com.zaya.nac.planocontas.model.Parceiro;
import br.com.zaya.nac.planocontas.model.RegraContaContabil;
import java.math.BigDecimal;

public interface PlanoContasRepository {

    BigDecimal buscarEmpresaPlano(BigDecimal codigoEmpresa) throws Exception;

    ContaCriada criarConta(
            BigDecimal empresaPlano,
            BigDecimal usuario,
            Parceiro parceiro,
            RegraContaContabil regra) throws Exception;
}

