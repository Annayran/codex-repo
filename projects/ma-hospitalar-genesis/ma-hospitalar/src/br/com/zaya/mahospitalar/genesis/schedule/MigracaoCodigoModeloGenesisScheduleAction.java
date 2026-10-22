package br.com.zaya.mahospitalar.genesis.schedule;

import br.com.zaya.mahospitalar.genesis.client.*;
import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.repository.*;
import br.com.zaya.mahospitalar.genesis.service.*;
import br.com.zaya.mahospitalar.genesis.util.*;
import org.cuckoo.core.ScheduledAction;
import org.cuckoo.core.ScheduledActionContext;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MigracaoCodigoModeloGenesisScheduleAction implements ScheduledAction {
    private static final AtomicBoolean EXECUTANDO=new AtomicBoolean();
    @Override public void onTime(ScheduledActionContext context){
        if(!EXECUTANDO.compareAndSet(false,true))return;
        GenesisExecutionLog log=new GenesisExecutionLog("MIGRACAO_MODELO_HSP",context::info);
        MigracaoModeloGenesisRepository repo=new MigracaoModeloGenesisRepository();
        try{
            Settings s=GenesisTransaction.execute(jdbc->{
                if(new GenesisCooldownRepository().emEspera(jdbc))return null;
                ConfigGenesisRepository config=new ConfigGenesisRepository();
                ParametrosGenesis p=config.carregar(jdbc);p.validarParaExecucao();
                if(p.isSimulacao())return null;
                return new Settings(p,config.carregarTokenHomologacao(jdbc),repo.proximo(jdbc));
            });
            if(s==null||s.alvo==null){log.info("Sem migracao elegivel, em simulacao ou cooldown; nenhuma chamada.");return;}
            final MigracaoModeloGenesisRepository.Alvo a=s.alvo;
            log.registro("CODPROD="+a.produto+", IDMODELO="+a.id);log.controle(a.controle().chaveMovimento);
            if(!GenesisTransaction.execute(jdbc->repo.reservar(jdbc,a))){log.info("Migracao ja reservada; nenhuma chamada.");return;}
            try{
                GenesisTransaction.execute(jdbc->{repo.validarLease(jdbc,a);return null;});
                MigracaoModeloGenesisService.Resultado r=new MigracaoModeloGenesisService(
                        new GenesisHttpClient(s.parametros,()->s.token,log)).migrar(a.id,a.anterior,a.destino,()->{
                            GenesisTransaction.execute(jdbc->{repo.validarLease(jdbc,a);return null;});
                        });
                GenesisRequestRepository.identidade(r.consulta);GenesisRequestRepository.identidade(r.confirmacao);
                if(r.put!=null)GenesisRequestRepository.identidade(r.put);
                try{GenesisTransaction.execute(jdbc->{repo.confirmar(jdbc,a,r);return null;});}
                catch(Exception local){
                    try{GenesisRequestRepository.falhaPersistencia(r.confirmacao,local);}catch(Exception rastreio){local.addSuppressed(rastreio);}
                    throw local;
                }
                log.info("Modelo reconciliado por ID; codigo anterior="+a.anterior+", HSP_IDEXTERNO="+a.destino+", PUT="+(r.put!=null)+"; sem POST.");
            }catch(Exception e){
                Integer http=e instanceof GenesisSyncService.FalhaIntegracao?((GenesisSyncService.FalhaIntegracao)e).resultado.getHttpStatus():null;
                GenesisTransaction.execute(jdbc->{repo.falhar(jdbc,a,http,e.getMessage());return null;});
                log.info("Migracao pendente; nenhum POST autorizado: "+GenesisSanitizer.sanitize(e.getMessage()));
            }
        }catch(Exception e){log.info("Falha geral da migracao: "+GenesisSanitizer.sanitize(e.getMessage()));throw new IllegalStateException("Migracao de modelos interrompida.",e);}
        finally{EXECUTANDO.set(false);log.finalizar();}
    }
    private static final class Settings{
        final ParametrosGenesis parametros;final String token;final MigracaoModeloGenesisRepository.Alvo alvo;
        Settings(ParametrosGenesis p,String token,MigracaoModeloGenesisRepository.Alvo a){this.parametros=p;this.token=token;this.alvo=a;}
    }
}
