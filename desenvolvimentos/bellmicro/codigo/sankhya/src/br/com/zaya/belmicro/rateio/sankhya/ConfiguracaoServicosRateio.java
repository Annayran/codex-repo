package br.com.zaya.belmicro.rateio.sankhya;

import br.com.zaya.belmicro.rateio.aplicacao.porta.*;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.*;
import br.com.zaya.belmicro.rateio.infraestrutura.persistencia.TabelasAdRat;
import java.util.Map;

/** Backend-only configuration: actions never depend on a dashboard/modal launcher. */
public interface ConfiguracaoServicosRateio {
    default Map<TabelaRateio,String> instanciasJape(){return InstanciasRateio.confirmadas();}
    default OrigemRepository origens(ConexaoRateio conexao){return new OrigemRepositoryJdbc(conexao,mapeamentoOrigem());}
    default MapeamentoOrigem mapeamentoOrigem(){return (origem,conexao)->MapeamentoOrigem.BaseCompetencia.pendente();}
    AutorizacaoRateio autorizacao(ConexaoRateio conexao);
    default EfetivacaoRateio efetivacao(TabelasAdRat tabelas,ConexaoRateio conexao){
        return (origem,id,revisao,hash,chave,usuario)->{
            throw new ErroRateio("EFETIVACAO_NAO_DISPONIVEL","Servico de efetivacao financeira ainda nao implementado e homologado. Nenhum titulo foi criado.");
        };
    }
}
