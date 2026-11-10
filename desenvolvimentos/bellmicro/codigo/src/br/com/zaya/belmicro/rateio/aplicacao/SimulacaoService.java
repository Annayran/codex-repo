package br.com.zaya.belmicro.rateio.aplicacao;

import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.aplicacao.porta.*;
import br.com.zaya.belmicro.rateio.dominio.*;
import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.ArrayList;

/** Application logic only; production persistence and authenticated transport are external ports. */
public final class SimulacaoService {
    private final OrigemRepository origens;
    private final CriterioRepository criterios;
    private final SimulacaoRepository simulacoes;
    private final ExecucaoRepository execucoes;
    private final AutorizacaoRateio autorizacao;
    private final Clock relogio;
    private final CalculadoraRateio calculadora = new CalculadoraRateio();

    public SimulacaoService(OrigemRepository origens, CriterioRepository criterios,
                            SimulacaoRepository simulacoes, ExecucaoRepository execucoes,
                            AutorizacaoRateio autorizacao, Clock relogio) {
        this.origens = Objects.requireNonNull(origens,"origens");
        this.criterios = Objects.requireNonNull(criterios,"criterios");
        this.simulacoes = Objects.requireNonNull(simulacoes,"simulacoes");
        this.execucoes = Objects.requireNonNull(execucoes,"execucoes");
        this.autorizacao = Objects.requireNonNull(autorizacao,"autorizacao");
        this.relogio = Objects.requireNonNull(relogio,"relogio");
    }

    /** Read only: no revision update, recalculation or automatic financial action. */
    public ContextoModal abrirContexto(OrigemCanonica referencia, long usuario) {
        DadosOrigem dados = resolverAutorizada(referencia,usuario,AutorizacaoRateio.Operacao.CONSULTAR);
        boolean efetivada = execucoes.concluidaDe(dados.getOrigem());
        List<String> pendencias=new ArrayList<String>();
        if(dados.getBase()==null||dados.getBase().signum()<=0||dados.getBase().stripTrailingZeros().scale()>2||dados.getCompetencia()==null)
            pendencias.add("BASE_INCOMPLETA");
        List<CriterioDisponivel> disponiveis=Collections.emptyList();
        try{disponiveis=criterios.aplicaveis(dados);}
        catch(ErroRateio e){if(!pendenciaFuncional(e.getCodigo()))throw e;if(!pendencias.contains(e.getCodigo()))pendencias.add(e.getCodigo());}
        return new ContextoModal(dados, disponiveis,
                simulacoes.correnteDe(dados.getOrigem()),efetivada,
                !efetivada && autorizacao.permite(usuario,dados.getOrigem(),AutorizacaoRateio.Operacao.SIMULAR),pendencias);
    }

    public SimulacaoRateio simular(OrigemCanonica referencia, EntradaSimulacao entrada,
                                   long usuario, String solicitacaoId) {
        validarComando(entrada,solicitacaoId);
        DadosOrigem inicial = resolverAutorizada(referencia,usuario,AutorizacaoRateio.Operacao.SIMULAR);
        OrigemCanonica origem = inicial.getOrigem();
        String identidade = IdentidadeSolicitacao.calcular("SIMULAR",origem,0,0,entrada,null,usuario,referenciaMapeada(inicial));
        return simulacoes.emTransacao(origem,solicitacaoId,() -> {
            DadosOrigem dados = resolverAutorizada(referencia,usuario,AutorizacaoRateio.Operacao.SIMULAR);
            exigirMesmaOrigem(origem,dados);
            SimulacaoRateio repetida = repetida(solicitacaoId,identidade);
            if (repetida != null) return repetida;
            exigirNaoEfetivada(origem);
            if (simulacoes.correnteDe(origem) != null) {
                throw erro("SIMULACAO_DESATUALIZADA","Existe simulacao. Recalcule informando a revisao corrente.");
            }
            return registrar(dados,entrada,simulacoes.idPara(dados,entrada,usuario,relogio.instant()),1,usuario,null,solicitacaoId,identidade);
        });
    }

    public SimulacaoRateio recalcular(long simulacaoId, int revisaoEsperada, EntradaSimulacao entrada,
                                      String motivo, long usuario, String solicitacaoId) {
        return recalcularInterno(null,simulacaoId,revisaoEsperada,entrada,motivo,usuario,solicitacaoId);
    }
    /** Bind the Financeiro row selected by the caller to the persisted snapshot, including retries. */
    public SimulacaoRateio recalcularParaOrigem(OrigemCanonica referencia,long simulacaoId,int revisaoEsperada,
                                               EntradaSimulacao entrada,String motivo,long usuario,String solicitacaoId){
        if(referencia==null)throw erro("BASE_INCOMPLETA","Selecione uma origem financeira.");
        return recalcularInterno(referencia,simulacaoId,revisaoEsperada,entrada,motivo,usuario,solicitacaoId);
    }
    private SimulacaoRateio recalcularInterno(OrigemCanonica origemSelecionada,long simulacaoId,int revisaoEsperada,
                                              EntradaSimulacao entrada,String motivo,long usuario,String solicitacaoId){
        validarComando(entrada,solicitacaoId);
        if (simulacaoId <= 0 || revisaoEsperada <= 0) {
            throw erro("SIMULACAO_DESATUALIZADA","Identificador e revisao devem ser positivos.");
        }
        if (motivo == null || motivo.trim().isEmpty() || motivo.length() > 255) {
            throw erro("COMPOSICAO_INVALIDA","Informe um motivo de ate 255 caracteres, sem dados pessoais.");
        }
        SimulacaoRateio referencia = simulacoes.correntePorId(simulacaoId);
        if (referencia == null) throw erro("SIMULACAO_DESATUALIZADA","Simulacao indisponivel.");
        OrigemCanonica origem = referencia.getOrigem();
        OrigemCanonica leitura=origemSelecionada!=null?origemSelecionada:referenciaLeitura(referencia.getDadosOrigem());
        if(usuario<=0)throw erro("SEM_PERMISSAO","Sessao autenticada obrigatoria.");
        autorizacao.exigir(usuario,origem,AutorizacaoRateio.Operacao.SIMULAR);
        if(origemSelecionada==null){
            SimulacaoRepository.Solicitacao replay=simulacoes.porSolicitacao(solicitacaoId);
            if(replay!=null&&origem.equals(replay.getResultado().getOrigem())&&replay.getResultado().getUsuario()==usuario){
                leitura=referenciaLeitura(replay.getResultado().getDadosOrigem());
            }
        }
        DadosOrigem inicial=resolverAutorizada(leitura,usuario,AutorizacaoRateio.Operacao.SIMULAR);
        exigirMesmaOrigem(origem,inicial);
        final OrigemCanonica referenciaConsulta=leitura;
        String identidade = IdentidadeSolicitacao.calcular("RECALCULAR",origem,simulacaoId,revisaoEsperada,entrada,motivo,usuario,referenciaMapeada(inicial));
        return simulacoes.emTransacao(origem,solicitacaoId,() -> {
            DadosOrigem dados = resolverAutorizada(referenciaConsulta,usuario,AutorizacaoRateio.Operacao.SIMULAR);
            exigirMesmaOrigem(origem,dados);
            SimulacaoRateio repetida = repetida(solicitacaoId,identidade);
            if (repetida != null) return repetida;
            exigirNaoEfetivada(origem);
            SimulacaoRateio corrente = simulacoes.correnteDe(origem);
            if (corrente == null || corrente.getId() != simulacaoId || corrente.getRevisao() != revisaoEsperada) {
                throw erro("SIMULACAO_DESATUALIZADA","Atualize a consulta e analise a revisao corrente.");
            }
            return registrar(dados,entrada,simulacaoId,Math.addExact(revisaoEsperada,1),usuario,motivo,solicitacaoId,identidade);
        });
    }

    private SimulacaoRateio registrar(DadosOrigem dados, EntradaSimulacao entrada, long id, int revisao,
                                      long usuario, String motivo, String solicitacaoId, String identidade) {
        ResultadoCalculo resultado = null;
        String hash = null;
        String pendencia = null;
        try {
            List<DestinoPeso> destinos;
            if (entrada.getModalidade() == EntradaSimulacao.Modalidade.MANUAL) {
                destinos = ComposicaoManual.validar(entrada.destinosManuais());
            } else {
                boolean aplicavel = false;
                for (CriterioDisponivel criterio : criterios.aplicaveis(dados)) {
                    if (criterio.getId().equals(entrada.getCriterioId()) && criterio.getVersao() == entrada.getCriterioVersao()) {
                        if (aplicavel) throw erro("REGRA_CONFLITANTE","Configuracao de criterio ambigua.");
                        aplicavel = true;
                    }
                }
                if (!aplicavel) throw erro("CRITERIO_NAO_SELECIONADO","Criterio indisponivel para esta origem.");
                destinos = criterios.carregarPesos(entrada.getCriterioId(),entrada.getCriterioVersao(),dados);
            }
            origens.validarDestinos(dados,destinos);
            ResultadoCalculo calculado = calculadora.calcular(dados.getBase(),destinos,dados.getCodEmp());
            hash = HashComposicao.calcular(dados.contextoHash(entrada,calculado));
            resultado = calculado;
        } catch (ErroRateio e) {
            if (!pendenciaFuncional(e.getCodigo())) throw e;
            // Only a stable code is persisted; adapter messages may contain private information.
            pendencia = e.getCodigo();
        }
        SimulacaoRateio simulacao = new SimulacaoRateio(id,revisao,dados,entrada,resultado,hash,
                pendencia == null ? SimulacaoRateio.Situacao.VALIDA : SimulacaoRateio.Situacao.COM_PENDENCIA,
                pendencia,usuario,relogio.instant(),motivo);
        simulacoes.salvarNovaRevisao(simulacao);
        simulacoes.registrarSolicitacao(solicitacaoId,identidade,simulacao);
        return simulacao;
    }

    private DadosOrigem resolverAutorizada(OrigemCanonica referencia, long usuario, AutorizacaoRateio.Operacao operacao) {
        if (referencia == null) throw erro("BASE_INCOMPLETA","Origem obrigatoria.");
        if (usuario <= 0) throw erro("SEM_PERMISSAO","Sessao autenticada obrigatoria.");
        autorizacao.exigir(usuario,referencia,operacao);
        DadosOrigem dados = origens.resolver(referencia);
        if (dados == null) throw erro("BASE_INCOMPLETA","Origem indisponivel.");
        autorizacao.exigir(usuario,dados.getOrigem(),operacao);
        if (dados.isAuxiliar()) throw erro("ORIGEM_AUXILIAR","Auxiliar nao pode ser origem de rateio.");
        return dados;
    }

    private SimulacaoRateio repetida(String chave, String identidade) {
        SimulacaoRepository.Solicitacao existente = simulacoes.porSolicitacao(chave);
        if (existente == null) return null;
        if (!identidade.equals(existente.getIdentidade())) {
            throw erro("SOLICITACAO_INCOMPATIVEL","Chave reutilizada com outra identidade.");
        }
        return existente.getResultado();
    }
    private void exigirNaoEfetivada(OrigemCanonica origem) {
        if (execucoes.concluidaDe(origem)) throw erro("RATEIO_JA_EFETIVADO","Consulte o resultado existente.");
    }
    private static void exigirMesmaOrigem(OrigemCanonica origem, DadosOrigem dados) {
        if (!origem.equals(dados.getOrigem())) throw erro("SIMULACAO_DESATUALIZADA","Origem canonica alterada.");
    }
    private static OrigemCanonica referenciaMapeada(DadosOrigem dados){
        String id=dados.getCamposMateriais().get("NUFIN_REFERENCIA");
        if(id==null)return null;
        try{long numero=Long.parseLong(id);if(numero<=0)throw new NumberFormatException();return new OrigemCanonica(OrigemCanonica.Tipo.TITULO,numero);}
        catch(NumberFormatException e){throw erro("SIMULACAO_DESATUALIZADA","Referencia financeira persistida invalida.");}
    }
    private static OrigemCanonica referenciaLeitura(DadosOrigem dados){
        OrigemCanonica referencia=referenciaMapeada(dados);return referencia==null?dados.getOrigem():referencia;
    }
    private static void validarComando(EntradaSimulacao entrada, String solicitacaoId) {
        if (entrada == null) throw erro("COMPOSICAO_INVALIDA","Entrada obrigatoria.");
        if (solicitacaoId == null || solicitacaoId.trim().isEmpty() || solicitacaoId.length() > 80) {
            throw erro("SOLICITACAO_INCOMPATIVEL","Chave de solicitacao obrigatoria, com ate 80 caracteres.");
        }
    }
    private static boolean pendenciaFuncional(String codigo) {
        return "BASE_INCOMPLETA".equals(codigo) || "COMPOSICAO_INVALIDA".equals(codigo)
                || "CADASTRO_INVALIDO".equals(codigo) || "REGRA_CONFLITANTE".equals(codigo)
                || "CRITERIO_NAO_SELECIONADO".equals(codigo);
    }
    private static ErroRateio erro(String codigo,String mensagem) { return new ErroRateio(codigo,mensagem); }
}
