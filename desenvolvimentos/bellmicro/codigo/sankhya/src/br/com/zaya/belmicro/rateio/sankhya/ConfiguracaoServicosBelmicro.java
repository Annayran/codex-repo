package br.com.zaya.belmicro.rateio.sankhya;

import br.com.zaya.belmicro.rateio.aplicacao.porta.AutorizacaoRateio;
import br.com.zaya.belmicro.rateio.aplicacao.dto.OrigemCanonica;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.ConexaoRateio;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.MapeamentoOrigem;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.MapeamentoFinanceiroTgffin;

/** Approved TGFFIN mapping; fine-grained access temporarily deferred by the client. */
public final class ConfiguracaoServicosBelmicro implements ConfiguracaoServicosRateio {
    public ConfiguracaoServicosBelmicro() { }
    @Override public MapeamentoOrigem mapeamentoOrigem(){return new MapeamentoFinanceiroTgffin();}

    @Override public AutorizacaoRateio autorizacao(ConexaoRateio conexao) {
        // Explicit client instruction 29/10/2026: remove the pending-policy block for now.
        // This is not an implementation of Sankhya per-operation or company ACL checks.
        return new AutorizacaoRateio() {
            @Override public void exigir(long usuario,OrigemCanonica origem,Operacao operacao) {
                if(!permite(usuario,origem,operacao))throw new ErroRateio("SEM_PERMISSAO",
                        "Sessao, origem ou operacao invalida.");
            }
            @Override public boolean permite(long usuario,OrigemCanonica origem,Operacao operacao) {
                return usuario>0&&origem!=null&&origem.getId()>0&&operacao!=null;
            }
        };
    }
}
