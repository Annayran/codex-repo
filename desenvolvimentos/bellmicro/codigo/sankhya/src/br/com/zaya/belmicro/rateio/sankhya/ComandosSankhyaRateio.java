package br.com.zaya.belmicro.rateio.sankhya;

import br.com.sankhya.modelcore.auth.AuthenticationInfo;
import br.com.zaya.belmicro.rateio.aplicacao.*;
import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.*;
import br.com.zaya.belmicro.rateio.infraestrutura.persistencia.SimulacaoRepositoryAdRat;
import java.time.Clock;
import java.util.Objects;
import java.util.Map;

/** Register these handlers through the supported authenticated Sankhya transport. No external API. */
public final class ComandosSankhyaRateio implements OperacoesAcaoRateio {
    private final ConfiguracaoServicosRateio configuracao;
    public ComandosSankhyaRateio(ConfiguracaoServicosRateio configuracao){this.configuracao=Objects.requireNonNull(configuracao,"configuracao");}
    /** Preserve the constructor used by legacy integrations. */
    @Deprecated public ComandosSankhyaRateio(ConfiguracaoRateio configuracao){this((ConfiguracaoServicosRateio)configuracao);}
    public Map<String,Object> abrirContexto(OrigemCanonica origem)throws Exception{
        long usuario=usuarioAtual();
        try(PersistenciaJape persistencia=new PersistenciaJape(configuracao.instanciasJape())){
            // Authorization of selected and canonical origins precedes detail/catalog reads.
            ContextoModal contexto=servico(persistencia).abrirContexto(origem,usuario);
            Map<String,Object> resposta=new java.util.LinkedHashMap<String,Object>(DtoModalRateio.contexto(contexto));
            resposta.put("bancoTipo",BloqueioRateioJdbc.identificarBanco(persistencia.obter()));
            ConsultaModalRateioJdbc consulta=new ConsultaModalRateioJdbc(persistencia);
            if(origem.getTipo()==OrigemCanonica.Tipo.TITULO)resposta.put("financeiro",consulta.financeiro(origem.getId()));
            try{resposta.put("resumoFinanceiro",new ResumoFinanceiroJdbc(persistencia).carregar(contexto.getOrigem().getOrigem()).paraDto());}
            catch(ErroRateio e){
                if(!"BASE_INCOMPLETA".equals(e.getCodigo())&&!"CADASTRO_INVALIDO".equals(e.getCodigo()))throw e;
                java.util.List<String> pendencias=new java.util.ArrayList<String>(contexto.getPendencias());
                if(!pendencias.contains(e.getCodigo()))pendencias.add(e.getCodigo());resposta.put("pendencias",pendencias);
            }
            resposta.put("catalogos",consulta.catalogos());return resposta;
        }
    }
    public Map<String,Object> simular(OrigemCanonica origem,EntradaSimulacao entrada,String chave)throws Exception{return executar(c->DtoModalRateio.simulacao(c.simular(origem,entrada,chave)));}
    public Map<String,Object> recalcular(long id,int revisao,EntradaSimulacao entrada,String motivo,String chave)throws Exception{return executar(c->DtoModalRateio.simulacao(c.recalcular(id,revisao,entrada,motivo,chave)));}
    public Map<String,Object> recalcular(OrigemCanonica referencia,long id,int revisao,EntradaSimulacao entrada,String motivo,String chave)throws Exception{
        long usuario=usuarioAtual();
        try(PersistenciaJape persistencia=new PersistenciaJape(configuracao.instanciasJape())){
            SimulacaoService servico=servico(persistencia);
            return DtoModalRateio.simulacao(servico.recalcularParaOrigem(referencia,id,revisao,entrada,motivo,usuario,chave));
        }
    }
    public Map<String,Object> efetivar(OrigemCanonica referencia,long id,int revisao,String hash,String chave)throws Exception{
        long usuario=usuarioAtual();
        try(PersistenciaJape persistencia=new PersistenciaJape(configuracao.instanciasJape())){
            // Configured effectuation owns all authorization, origin checks, locks and financial validation.
            return configuracao.efetivacao(persistencia,persistencia).efetivar(referencia,id,revisao,hash,chave,usuario);
        }
    }
    private <T>T executar(Comando<T> comando)throws Exception{
        long usuario=usuarioAtual();
        try(PersistenciaJape persistencia=new PersistenciaJape(configuracao.instanciasJape())){
            SimulacaoService servico=servico(persistencia);
            return comando.executar(new ComandosModalRateio(servico,()->{
                if(usuarioAtual()!=usuario)throw new ErroRateio("SEM_PERMISSAO","Sessao alterada.");return usuario;
            }));
        }
    }
    private SimulacaoService servico(PersistenciaJape persistencia){return new SimulacaoService(configuracao.origens(persistencia),new CriterioRepositoryJdbc(persistencia),
            new SimulacaoRepositoryAdRat(persistencia),new ExecucaoRepositoryJdbc(persistencia),configuracao.autorizacao(persistencia),Clock.systemUTC());}
    public static long usuarioAtual(){
        AuthenticationInfo auth=AuthenticationInfo.getCurrentOrNull();
        if(auth==null||!auth.isValid()||auth.getUserID()==null)throw new ErroRateio("SEM_PERMISSAO","Sessao autenticada obrigatoria.");
        long id=auth.getUserID().longValueExact();if(id<=0)throw new ErroRateio("SEM_PERMISSAO","Usuario invalido.");return id;
    }
    private interface Comando<T>{T executar(ComandosModalRateio comandos);}
}
