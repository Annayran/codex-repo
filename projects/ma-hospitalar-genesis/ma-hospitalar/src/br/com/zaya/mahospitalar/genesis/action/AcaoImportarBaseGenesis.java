package br.com.zaya.mahospitalar.genesis.action;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.zaya.mahospitalar.genesis.importacao.ImportacaoBaseGenesisService;
import br.com.zaya.mahospitalar.genesis.importacao.ResultadoLoteGenesis;

import java.math.BigDecimal;

public final class AcaoImportarBaseGenesis implements AcaoRotinaJava {
    private static final String LOG = "[MAH-GENESIS-IMPORT] ";

    @Override
    public void doAction(ContextoAcao contexto) throws Exception {
        BigDecimal idBase = resolverIdBase(contexto);
        String operacao = texto(contexto.getParam("OPERACAO"));
        ImportacaoBaseGenesisService service = new ImportacaoBaseGenesisService();

        try {
            if ("INICIAR".equalsIgnoreCase(operacao)) {
                service.iniciar(idBase, texto(contexto.getParam("ARQUIVO")), contexto.getUsuarioLogado());
                registrar("Importacao iniciada. idBase=" + idBase);
                contexto.setMensagemRetorno("Base preparada para importacao.");
                return;
            }
            if ("FINALIZAR".equalsIgnoreCase(operacao)) {
                service.finalizar(idBase);
                registrar("Importacao finalizada. idBase=" + idBase);
                contexto.setMensagemRetorno("Base marcada como IMPORTADA.");
                return;
            }
            if (!"IMPORTAR_LOTE".equalsIgnoreCase(operacao)) {
                throw new IllegalArgumentException("Operacao de importacao invalida.");
            }

            String aba = texto(contexto.getParam("ABA"));
            String dados = texto(contexto.getParam("DADOS_JSON"));
            int linhaInicial = inteiro(contexto.getParam("LINHA_INICIAL"), 2);
            ResultadoLoteGenesis resultado = service.importar(idBase, aba, dados, linhaInicial);
            registrar("Lote concluido. idBase=" + idBase + ", aba=" + aba + ", " + resultado.resumir());
            contexto.setMensagemRetorno(resultado.resumir());
        } catch (Exception e) {
            System.err.println(LOG + "Falha. idBase=" + idBase + ", operacao=" + operacao
                    + ", motivo=" + mensagemSegura(e));
            throw e;
        }
    }

    private BigDecimal resolverIdBase(ContextoAcao contexto) {
        Object parametro = contexto.getParam("IDBASE");
        if (parametro instanceof BigDecimal) return (BigDecimal) parametro;
        if (parametro instanceof Number) return new BigDecimal(parametro.toString());
        if (parametro != null && !parametro.toString().trim().isEmpty()) {
            return new BigDecimal(parametro.toString().trim());
        }
        Registro[] linhas = contexto.getLinhas();
        if (linhas != null && linhas.length == 1) {
            Object campo = linhas[0].getCampo("IDBASE");
            if (campo instanceof BigDecimal) return (BigDecimal) campo;
            if (campo instanceof Number) return new BigDecimal(campo.toString());
        }
        throw new IllegalArgumentException("Selecione exatamente uma Base Instalada Genesis.");
    }

    private String texto(Object valor) {
        return valor == null ? "" : valor.toString().trim();
    }

    private int inteiro(Object valor, int padrao) {
        String texto = texto(valor);
        if (texto.isEmpty()) return padrao;
        return new BigDecimal(texto).intValueExact();
    }

    private String mensagemSegura(Exception e) {
        String mensagem = e.getMessage();
        if (mensagem == null || mensagem.trim().isEmpty()) return e.getClass().getSimpleName();
        return mensagem.replace('\r', ' ').replace('\n', ' ');
    }

    private void registrar(String mensagem) {
        System.out.println(LOG + mensagem);
    }
}
