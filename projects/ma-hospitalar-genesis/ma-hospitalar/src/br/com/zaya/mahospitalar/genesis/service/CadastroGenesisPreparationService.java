package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.config.ParametrosGenesis;
import br.com.zaya.mahospitalar.genesis.domain.*;
import br.com.zaya.mahospitalar.genesis.repository.BaseGenesisApiRepository;
import br.com.zaya.mahospitalar.genesis.repository.GenesisOperationRepository;
import br.com.zaya.mahospitalar.genesis.util.GenesisDiagnosticLog;
import br.com.zaya.mahospitalar.genesis.util.GenesisTransaction;

public final class CadastroGenesisPreparationService {
    private final ParametrosGenesis parametros;
    private final GenesisCadastroSyncService sync;
    private final GenesisOperationRepository operacoes;
    private final GenesisDiagnosticLog log;
    private final PayloadGenesisFactory factory;

    public CadastroGenesisPreparationService(ParametrosGenesis p, GenesisCadastroSyncService sync,
                                              GenesisOperationRepository operacoes, GenesisDiagnosticLog log) {
        this.parametros = p; this.sync = sync; this.operacoes = operacoes; this.log = log;
        this.factory = new PayloadGenesisFactory(p.getUnidadeEpoch());
    }

    public boolean preparar(CandidatoGenesis c, ContextoBaseInstaladaGenesis l) throws Exception {
        if (!etapa(c, l, factory.cliente(c), l.cliente)) return false;
        if (!etapa(c, l, factory.setor(c, l.cliente.getIdGenesis().toString()), l.setor)) return false;
        if (!etapa(c, l, factory.material(c), l.material)) return false;
        if (!etapa(c, l, factory.fabricanteParaResolucao(c), l.fabricante)) return false;
        if (!etapa(c, l, factory.modelo(c, l.fabricante.getIdGenesis().toString(),
                l.material.getIdGenesis().toString()), l.modelo)) return false;
        return true;
    }

    private boolean etapa(CandidatoGenesis c, ContextoBaseInstaladaGenesis l,
                           PayloadGenesis novo, ReferenciaGenesisLocal local) throws Exception {
        PayloadGenesis p = factory.preservarCodigoExterno(novo, local.getCodigoExterno());
        if (p.getTipo() == TipoEntidade.MODELO && !parametros.isSimulacao()) {
            GenesisTransaction.execute(jdbc -> {
                new br.com.zaya.mahospitalar.genesis.repository.MigracaoModeloGenesisRepository()
                        .validarFonte(jdbc,c.codProd,p.getCodigoExterno()); return null;
            });
        }
        boolean pronto = GenesisTransaction.execute(jdbc -> operacoes.sincronizado(jdbc, p, local.getIdGenesis()));
        if (pronto) return true;
        if(log instanceof br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog)
            ((br.com.zaya.mahospitalar.genesis.util.GenesisExecutionLog)log).controle("API|"+p.getTipo().name()+"|"+p.getChaveSankhya());
        GenesisTransaction.execute(jdbc -> {
            new br.com.zaya.mahospitalar.genesis.repository.MovimentoGenesisRepository()
                    .etapa(jdbc,c,"RESOLVER_CADASTRO",p.getTipo().name()); return null;
        });
        GenesisSyncService.Resultado r = sync.resolver(p, local.getIdGenesis());
        if (!r.simulado) {
            try {
            GenesisTransaction.execute(jdbc -> {
                new BaseGenesisApiRepository().persistir(jdbc, c, l, r, parametros.getUnidadeEpoch());
                operacoes.sincronizado(jdbc, p);
                new br.com.zaya.mahospitalar.genesis.repository.MovimentoGenesisRepository()
                        .etapa(jdbc,c,"AGUARDANDO_PROXIMA_ETAPA",null);
                return null;
            });
            } catch (Exception erroLocal) {
                try { br.com.zaya.mahospitalar.genesis.repository.GenesisRequestRepository.falhaPersistencia(r.api,erroLocal); }
                catch (Exception erroRastreio) { erroLocal.addSuppressed(erroRastreio); }
                throw erroLocal;
            }
        }
        log.info("Cadastro processado; entidade=" + p.getTipo() + "; operacao=" + r.operacao +
                "; id=" + r.api.getIdGenesis() + "; IDBASE=1.");
        return false;
    }
}
