package br.com.zaya.belmicro.rateio.acao;

import br.com.sankhya.extensions.actionbutton.*;
import br.com.zaya.belmicro.rateio.aplicacao.dto.*;
import br.com.zaya.belmicro.rateio.dominio.ErroRateio;
import br.com.zaya.belmicro.rateio.infraestrutura.jdbc.FalhaConsultaRateio;
import br.com.zaya.belmicro.rateio.sankhya.OperacoesAcaoRateio;
import java.math.BigDecimal;
import java.util.*;

public abstract class AcaoFinanceiroRateio {
    enum Tipo{SIMULAR,RECALCULAR,EFETIVAR}
    private final Tipo tipo;
    private final RuntimeAcoesRateio runtime;
    AcaoFinanceiroRateio(Tipo tipo){this(tipo,new RuntimeAcoesRateio());}
    AcaoFinanceiroRateio(Tipo tipo,RuntimeAcoesRateio runtime){this.tipo=tipo;this.runtime=runtime;}
    public final void doAction(ContextoAcao ctx)throws Exception{
        DiagnosticoAcaoRateio diagnostico=new DiagnosticoAcaoRateio(tipo.name());String operacao=tipo.name();
        try{
            diagnostico.etapa("VALIDAR_SESSAO");
            long usuario=runtime.usuario();
            if(usuario<=0||ctx.getUsuarioLogado()==null||ctx.getUsuarioLogado().longValueExact()!=usuario)throw new ErroRateio("SEM_PERMISSAO","Sessao divergente.");
            diagnostico.etapa("VALIDAR_SELECAO");
            Registro[] linhas=ctx.getLinhas();if(linhas==null||linhas.length!=1)throw new ErroRateio("BASE_INCOMPLETA","Selecione um unico titulo na instancia Financeiro.");
            Object campo=linhas[0].getCampo("NUFIN");long nufin;
            try{nufin=new BigDecimal(String.valueOf(campo)).longValueExact();if(nufin<=0)throw new ArithmeticException();}
            catch(NumberFormatException|ArithmeticException e){throw new ErroRateio("BASE_INCOMPLETA","NUFIN deve ser inteiro positivo.");}
            OrigemCanonica referencia=new OrigemCanonica(OrigemCanonica.Tipo.TITULO,nufin);
            diagnostico.etapa("LER_PARAMETROS");
            ParametrosAcaoRateio p=new ParametrosAcaoRateio(ctx.getParam("RATEIO_JSON"));
            Map<String,Object> dados;
            // Validate complete payload before resolving any installation provider or mutating state.
            if(tipo==Tipo.SIMULAR&&p.consultar()){
                operacao="CONSULTAR";diagnostico.operacao(operacao);p.campos("modo");diagnostico.etapa("CONSULTAR_CONTEXTO");dados=runtime.operacoes().abrirContexto(referencia);
            }else if(tipo==Tipo.SIMULAR){
                diagnostico.etapa("VALIDAR_COMPOSICAO");
                p.campos("modo","entrada","solicitacaoId");p.exigirModoSimular();EntradaSimulacao entrada=p.entrada();String chave=p.chave();
                diagnostico.etapa("SIMULAR");dados=runtime.operacoes().simular(referencia,entrada,chave);
            }else if(tipo==Tipo.RECALCULAR){
                diagnostico.etapa("VALIDAR_RECALCULO");
                p.campos("simulacaoId","revisaoEsperada","entrada","motivo","solicitacaoId");
                long id=p.simulacao();int revisao=p.revisao();EntradaSimulacao entrada=p.entrada();String motivo=p.motivo(),chave=p.chave();
                diagnostico.etapa("RECALCULAR");dados=runtime.operacoes().recalcular(referencia,id,revisao,entrada,motivo,chave);
            }else{
                diagnostico.etapa("VALIDAR_EFETIVACAO");
                p.campos("simulacaoId","revisaoEsperada","hashEsperado","solicitacaoId");
                long id=p.simulacao();int revisao=p.revisao();String hash=p.hash(),chave=p.chave();
                diagnostico.etapa("EFETIVAR");dados=runtime.operacoes().efetivar(referencia,id,revisao,hash,chave);
            }
            Map<String,Object> resposta=new LinkedHashMap<String,Object>();resposta.put("sucesso",true);resposta.put("operacao",operacao);resposta.put("dados",dados);
            if(dados.get("bancoTipo") instanceof String)diagnostico.bancoTipo((String)dados.get("bancoTipo"));
            diagnostico.etapa("RETORNAR_RESULTADO");resposta.put("diagnostico",diagnostico.concluir("SUCESSO",null));
            ctx.setMensagemRetorno(DiagnosticoAcaoRateio.jsonAscii(resposta));
        }catch(ErroRateio e){
            String codigo=e.getCodigo()!=null&&e.getCodigo().matches("[A-Z_]{1,60}")?e.getCodigo():"ERRO_TECNICO";
            Map<String,Object> erro=new LinkedHashMap<String,Object>();erro.put("sucesso",false);erro.put("operacao",operacao);erro.put("codigo",codigo);
            erro.put("mensagem","COMPOSICAO_INVALIDA".equals(codigo)?DiagnosticoAcaoRateio.detalheComposicao(e.getMessage()):mensagemPublica(codigo));
            erro.put("orientacao","ERRO_TECNICO".equals(codigo)?"Tente novamente com a mesma solicitação antes de alterar os dados.":"Revise a informação indicada e envie novamente.");
            erro.put("diagnostico",diagnostico.concluir(codigo,null));
            // Throw, do not return a success envelope after a transactional failure.
            throw new ErroRateio(codigo,DiagnosticoAcaoRateio.jsonAscii(erro));
        }catch(Exception e){
            String etapa="ACAO",bancoTipo=null;Integer banco=null;
            if(e instanceof FalhaConsultaRateio){
                FalhaConsultaRateio falha=(FalhaConsultaRateio)e;
                String op=falha.getOperacao();
                if(op!=null&&op.matches("(?:ABRIR_JAPE(?:_TX)?|TRANSACAO_JAPE|IDENTIFICAR_BANCO|DIALETO_NAO_SUPORTADO|(?:LER|CONFIGURAR|RESTAURAR)_LOCK_TIMEOUT|BLOQUEAR_ORIGEM|BLOQUEAR_SIMULACOES|LER_AD_RAT|REFERENCIA_FINANCEIRA|RESUMO_FINANCEIRO|(?:INSERIR|ATUALIZAR|SUBSTITUIR)_(?:SIMULACAO|PARCELA|GRUPO|INTERNO|EVENTO))"))etapa=op;
                if(falha.getCodigoBanco()!=null&&falha.getCodigoBanco()!=0)banco=falha.getCodigoBanco();
                bancoTipo=falha.getBancoTipo();diagnostico.bancoTipo(bancoTipo);
            }
            diagnostico.etapa(etapa);Map<String,Object> detalhe=diagnostico.concluir("ERRO_TECNICO",banco);
            Map<String,Object> erro=new LinkedHashMap<String,Object>();erro.put("sucesso",false);erro.put("operacao",operacao);erro.put("codigo","ERRO_TECNICO");
            erro.put("mensagem","Não foi possível confirmar a operação. Etapa: "+etapa+"."+(bancoTipo==null?"":" Motor: "+bancoTipo+".")+(banco==null?"":" Banco: "+banco+".")+" Referencia: "+detalhe.get("referencia")+".");
            erro.put("orientacao","Tente novamente com a mesma solicitação antes de alterar os dados.");erro.put("diagnostico",detalhe);
            throw new ErroRateio("ERRO_TECNICO",DiagnosticoAcaoRateio.jsonAscii(erro));
        }
    }
    private static String mensagemPublica(String codigo){
        if("FOTOGRAFIA_EXCEDE_LIMITE".equals(codigo))return "A composicao excede o limite do historico em campos de 100 caracteres. Reduza a quantidade de destinos ou evidencias e simule novamente.";
        if("AUTORIZACAO_PENDENTE".equals(codigo))return "Permissoes do rateio ainda nao configuradas. Operacao indisponivel nesta etapa.";
        if("CONFIGURACAO_AUSENTE".equals(codigo))return "Configuracao de acesso e regras do rateio nao instalada no servidor. Solicite a configuracao antes de consultar.";
        if("EFETIVACAO_NAO_DISPONIVEL".equals(codigo))return "Efetivacao financeira ainda nao implementada e homologada. Nenhum titulo foi criado.";
        if("SEM_PERMISSAO".equals(codigo))return "Usuario sem permissao para esta operacao ou origem.";
        if("SIMULACAO_DESATUALIZADA".equals(codigo))return "Atualize a consulta e analise a revisao corrente.";
        if("BASE_INCOMPLETA".equals(codigo))return "Origem, base, competencia ou configuracao de servicos incompleta.";
        if("COMPOSICAO_INVALIDA".equals(codigo))return "Confira RATEIO_JSON, parametros e composicao da solicitacao.";
        return "Operacao nao concluida. Consulte o codigo retornado.";
    }
}
