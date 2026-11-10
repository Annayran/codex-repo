package br.com.zaya.belmicro.rateio.aplicacao;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import java.util.Objects;

/** Transport boundary: no command accepts a browser-supplied user or final monetary values. */
public final class ComandosModalRateio {
    public interface UsuarioDaSessao { long usuario(); }
    private final SimulacaoService servico;
    private final UsuarioDaSessao sessao;
    public ComandosModalRateio(SimulacaoService servico,UsuarioDaSessao sessao){this.servico=Objects.requireNonNull(servico,"servico");this.sessao=Objects.requireNonNull(sessao,"sessao");}
    public ContextoModal abrirContexto(OrigemCanonica origem){return servico.abrirContexto(origem,usuario());}
    public SimulacaoRateio simular(OrigemCanonica origem,EntradaSimulacao entrada,String solicitacaoId){return servico.simular(origem,entrada,usuario(),solicitacaoId);}
    public SimulacaoRateio recalcular(long id,int revisao,EntradaSimulacao entrada,String motivo,String solicitacaoId){return servico.recalcular(id,revisao,entrada,motivo,usuario(),solicitacaoId);}
    private long usuario(){long usuario=sessao.usuario();if(usuario<=0)throw new ErroRateio("SEM_PERMISSAO","Sessao autenticada obrigatoria.");return usuario;}
}
