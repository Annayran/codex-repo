package br.com.zaya.mahospitalar.genesis.schedule;

import br.com.sankhya.jape.core.JapeSession;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.modelcore.util.EntityFacadeFactory;
import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.repository.*;
import br.com.zaya.mahospitalar.genesis.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import java.sql.Timestamp;
import java.security.MessageDigest;

public final class EstoqueGenesisScheduleAction implements ScheduledAction {
    private static final java.util.concurrent.atomic.AtomicBoolean EXECUTANDO=new java.util.concurrent.atomic.AtomicBoolean();
    public void onTime(final ScheduledActionContext context) {
        if(!EXECUTANDO.compareAndSet(false,true))return;
        final br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog log =
                new br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog("ESTOQUES_PRECOS", context::info);
        JapeSession.SessionHandle session=null;
        try { log.info("Inicio da acao agendada."); session=JapeSession.open(); executar(log); }
        catch(Exception e){ log.info("Falha geral: tipo="+e.getClass().getSimpleName()+", motivo="+e.getMessage()); throw new IllegalStateException("Catalogo Genesis interrompido; revisar log/configuracao.",e); }
        finally{JapeSession.close(session);EXECUTANDO.set(false);log.finalizar();}
    }
    private void executar(br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog context) throws Exception {
        JdbcWrapper jdbc=null;
        try {
            jdbc=EntityFacadeFactory.getDWFFacade().getJdbcWrapper();jdbc.openSession();
            EstoqueGenesisRepository repo=new EstoqueGenesisRepository();
            if(!repo.instalado(jdbc)){ context.info("Catalogo desabilitado: cadastrar AD_GENESTCTRL pelo Construtor.");return; }
            
            if(new GenesisCooldownRepository().emEspera(jdbc)){context.info("Catalogo em espera por cooldown.");return;}
            ConfigGenesisRepository cfg=new ConfigGenesisRepository();ParametrosGenesis p=cfg.carregar(jdbc);p.validarParaExecucao();
            if(p.isSimulacao()){context.info("Simulacao: catalogo preservado, sem chamadas.");return;}
            EstoqueSaldoGenesisRepository saldos=new EstoqueSaldoGenesisRepository();
            saldos.validarEscopo(jdbc);
            final String token=cfg.carregarTokenHomologacao(jdbc);
            context.info("Configuracao: ambiente="+p.getAmbiente()+", tabelaPreco=101, lote=1.");
            EstoquePrecoSankhya precos=new EstoquePrecoSankhya();ObjectMapper mapper=new ObjectMapper();
            for(EstoqueGenesisRepository.Candidato c:repo.listar(jdbc,1,p.getMaxTentativas())){
                context.registro("CODPROD="+c.produto+", idLocal="+c.id);
                context.info("Candidato: ativo="+c.ativo+", tipo="+c.tipo+", statusRemoto="+c.statusRemoto+", hashConfirmado="+c.hash);
                br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> { new CancelamentoGenesisRepository().bloquearIntegracao(tx); repo.preparar(tx,c); return null; });
                try {
                    repo.validarIdentidadeFonte(jdbc,c);
                    if(EstoqueGenesisRepository.precisaReconciliar(c)){
                        GenesisApiResult consulta=new GenesisHttpClient(p,()->token,context).buscarPorCodigoExterno(
                                br.com.zaya.mahospitalar.genesis.domain.TipoEntidade.ESTOQUE,c.dto.codigoExterno);
                        if(!consulta.isSucesso()){
                            br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx->{
                                repo.falha(tx,c,"REPROCESSAR",consulta.getHttpStatus(),"GET HSP sem identidade confirmada; nenhum POST autorizado.",p.getMaxTentativas());return null;
                            });
                            continue;
                        }
                        EstoqueGenesisRepository.validarReconciliacao(c,consulta);
                        GenesisRequestRepository.identidade(consulta);
                        br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx->{repo.conciliarFonte(tx,c,consulta);return null;});
                        context.info("Identidade HSP reconciliada; payload sera avaliado na proxima rodada, sem mutacao remota nesta rodada.");
                        continue;
                    }
                    String hash=null;
                    if(c.elegivel()){
                        c.saldo=saldos.consultar(jdbc,c.produto);c.dto.qtd=c.saldo.total();
                        context.info("Saldo TGFEST: "+c.saldo.resumo()+"; HSP_IDEXTERNO="+c.dto.codigoExterno);
                        repo.fabricante(jdbc,c);
                        EstoquePrecoSankhya.Valores valores=precos.consultar(jdbc,c.produto,new Timestamp(System.currentTimeMillis()));
                        c.dto.vlrVenda=valores.venda;c.dto.vlrCusto=null;c.dto.servico="SV".equalsIgnoreCase(c.tipo);
                        context.info("Preco nativo resolvido: venda="+valores.venda+", custo omitido; payload="+mapper.writeValueAsString(c.dto));
                        hash=hash(mapper.writeValueAsBytes(c.dto));
                        if(c.id!=null && hash.equals(c.hash) && "Ativo".equals(c.statusRemoto)){context.info("SEM_ALTERACAO: hash confirmado; nenhuma chamada HTTP.");br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> { repo.observar(tx,c); return null; });continue;}
                    }else if("Inativo".equals(c.statusRemoto)){context.info("Inativacao ja confirmada; nenhuma chamada HTTP.");br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> { repo.observar(tx,c); return null; });continue;}
                    else if(c.id==null){
                        br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> { repo.falha(tx,c,"REVISAO",null,"Produto inativo/fora do tipo sem ID confirmado; reconciliar possivel envio anterior.",p.getMaxTentativas()); return null; });
                        continue;
                    }
                    EstoqueGenesisSyncService sync=new EstoqueGenesisSyncService(new GenesisHttpClient(p,()->token,context),()->{
                        br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx->{repo.reservarCriacao(tx,c);return null;});
                    });
                    EstoqueGenesisSyncService.Resultado r=sync.sincronizar(c.dto,c.tipo,null,c.ativo,c.id);
                    final String hashConfirmado=hash;
                    br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> { repo.confirmar(tx,c,r,hashConfirmado); return null; });
                    context.info("Persistencia local CONFIRMADA: idGenesis="+r.idGenesis+", hash="+hash);
                    context.info("Catalogo: CODPROD="+c.produto+", operacao="+r.operacao);
                }catch(GenesisSyncService.FalhaIntegracao e){
                    context.info("Falha HTTP: "+e.getMessage());
                    boolean transitorio=e.resultado.getClassificacao()==GenesisApiResult.Classificacao.TRANSITORIO;
                    br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> {
                        int http=e.resultado.getHttpStatus();
                        if("POST".equals(e.etapa)&&http>=400&&http<500&&http!=408&&http!=409)repo.liberarRejeicaoCriacao(tx,c);
                        repo.falha(tx,c,transitorio?"REPROCESSAR":"ERRO",http,e.getMessage(),p.getMaxTentativas());return null;
                    });
                    if(e.resultado.getHttpStatus()==429 || e.resultado.getClassificacao()==GenesisApiResult.Classificacao.AUTENTICACAO)break;
                }catch(Exception e){context.info("REVISAO: tipo="+e.getClass().getSimpleName()+", motivo="+e.getMessage());br.com.zaya.mahospitalar.genesis.util.GenesisTransaction.execute(tx -> { repo.falha(tx,c,"REVISAO",null,e.getMessage(),p.getMaxTentativas()); return null; });}
            }
        }finally{JdbcWrapper.closeSession(jdbc);}
    }
    private static String hash(byte[] bytes)throws Exception{
        byte[] digest=MessageDigest.getInstance("SHA-256").digest(bytes);StringBuilder s=new StringBuilder();
        for(byte b:digest)s.append(String.format("%02x",b&255));return s.toString();
    }
}
