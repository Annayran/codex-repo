package br.com.zaya.mahospitalar.genesis.contract;

import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class GenesisArtifactContractTest {
    @Test void cooldownSegueMonitorExistenteEBotaoRealPreservaEspera() throws Exception {
        String jsp = read("webapp/genesis/index.jsp");
        assertTrue(jsp.indexOf("class=\"cooldown-status\"") > jsp.indexOf("aria-labelledby=\"monitorTitle\""));
        assertTrue(jsp.contains("<snk:query var=\"monitorCooldown\">"));
        assertTrue(jsp.contains("${monitorCooldown.rows}"));
        assertTrue(jsp.contains("<c:out value=\"${cooldown.ATE}\""));
        String action = read("src/br/com/zaya/mahospitalar/genesis/action/AcaoReprocessarGenesis.java");
        assertTrue(action.contains("GenesisCooldown.prazoPersistidoMs"));
        assertTrue(action.indexOf("GenesisCooldown.prazoPersistidoMs") < action.indexOf("linha.setCampo(\"HTTP_STATUS\", null)"));
        String pack = read("tools/BuildGenesisHomologacao.ps1");
        assertTrue(pack.contains("webapp/genesis"));
        assertTrue(pack.contains("java/MAHospitalar.jar"));
        assertTrue(pack.contains("sql/genesis"));
        assertTrue(pack.contains("type=`\"text/css`\""));
    }
    @Test void cadastroManualNaoSolicitaEstruturasParalelas() throws Exception {
        String guia = read("sql/genesis/CADASTRO_MANUAL.md").toUpperCase();
        assertTrue(guia.contains("AD_GENMOVEST"));
        assertTrue(guia.contains("AD_VW_GENESIS_ESTOQUE"));
        assertTrue(guia.contains("NÃO CRIAR") || guia.contains("NAO CRIAR"));
        assertFalse(Files.exists(Paths.get("sql/genesis/00_schema.sql")));
    }

    @Test void pacoteIncluiMesmoJarERecursosGenesis() throws Exception {
        String build = read("build.gradle");
        assertTrue(build.contains("archiveFileName = 'MAHospitalar.jar'"));
        assertTrue(build.contains("genesisPackage"));
        assertTrue(build.contains("from(jar.archiveFile)"));
        assertTrue(Files.exists(Paths.get("webapp/genesis/index.jsp")));
        assertTrue(Files.exists(Paths.get("sql/genesis/CADASTRO_MANUAL.md")));
    }

    @Test void dashboardUsaTagsCompativeisESemEndpointInterno() throws Exception {
        String jsp = read("webapp/genesis/index.jsp");
        String javascript = read("webapp/genesis/assets/genesis.js");
        String css = read("webapp/genesis/assets/genesis.css");
        assertTrue(jsp.contains("http://java.sun.com/jstl/core_rt"));
        assertTrue(jsp.contains("/WEB-INF/tld/sankhyaUtil.tld"));
        assertTrue(jsp.contains("assets/genesis.css"));
        assertTrue(jsp.contains("brand-shell"));
        assertTrue(css.contains("--ma-blue: #0e6089"));
        assertTrue(css.contains("--ma-green: #8aac4a"));
        assertTrue(jsp.contains("PARTITION BY V.CODPROD,UPPER(TRIM(V.SERIE))"));
        assertFalse(jsp.contains("var=\"integracaoAtualTotais\""));
        assertTrue(jsp.contains("var=\"monitorIntegracao\""));
        assertTrue(jsp.contains("PARTITION BY GM.CHAVE_MOVIMENTO"));
        assertTrue(jsp.contains("Monitor da integração"));
        assertTrue(jsp.contains("class=\"action-bar\""));
        assertFalse(jsp.contains("class=\"cards current-cards\""));
        assertTrue(jsp.contains("class=\"monitor-body\""));
        assertTrue(jsp.contains("class=\"status-donut\""));
        assertTrue(jsp.contains("logo-ma-vertical.png"));
        assertTrue(jsp.contains("data-source=\"sent\">Envios</button>"));
        assertTrue(jsp.contains("WHERE X.RN_MOV=1"));
        assertFalse(jsp.contains("ROWNUM <= 2000"));
        assertTrue(jsp.contains("id=\"pageSize\""));
        assertTrue(jsp.contains("V.TIPO_ESTOQUE"));
        assertTrue(jsp.contains(">Equipamento</span>"));
        assertTrue(javascript.contains("sortDirection"));
        assertTrue(javascript.contains("paginationSummary"));
        assertTrue(javascript.contains("cell.colSpan = 12"));
        assertTrue(jsp.contains("class=\"error-detail-button\""));
        assertTrue(jsp.contains("id=\"errorDialog\""));
        assertTrue(javascript.contains("diagnoseError"));
        assertTrue(javascript.contains("extractApiMessages"));
        assertTrue(css.contains(".error-dialog"));
        assertTrue(css.contains(".section-heading"));
        assertTrue(css.contains("flex-wrap: wrap"));
        assertTrue(jsp.contains("TO_CHAR(V.DTNEG,'DD/MM/YYYY') ULTIMA_MOVIMENTACAO"));
        assertTrue(jsp.contains("data-column=\"8\">O.S.</button>"));
        assertTrue(jsp.contains("value=\"${row.NUNOTA}\""));
        assertTrue(jsp.contains("id=\"equipmentFilter\""));
        assertTrue(jsp.contains("id=\"codeFilter\""));
        assertTrue(jsp.contains("id=\"documentFilter\""));
        assertTrue(jsp.contains("id=\"customerFilter\""));
        assertTrue(javascript.contains("equipment: { element: document.getElementById('equipmentFilter'), column: 5 }"));
        assertTrue(jsp.contains("CLI.IDGENESIS=I.IDCLIENTEGEN"));
        assertTrue(jsp.contains("ENDERECO_CLIENTE"));
        assertTrue(javascript.contains("enableColumnResize"));
        assertTrue(css.contains(".column-resizer"));
        assertTrue(jsp.contains("var=\"itensInstalados\""));
        assertTrue(jsp.contains("FROM AD_GENITEMMAT I"));
        assertFalse(jsp.contains("ROWNUM &lt;= 2000"));
        assertTrue(jsp.contains("row.IDMODELOGEN"));
        assertTrue(jsp.contains("row.IDCLIENTEGEN"));
        assertTrue(jsp.contains("row.IDSETORGEN"));
        assertTrue(jsp.contains("data-source=\"sent\""));
        assertTrue(jsp.contains("data-source=\"installed\""));
        assertTrue(javascript.contains("serverMode = activeSource === 'installed'"));
        assertTrue(javascript.contains("navigateInstalled"));
        assertTrue(javascript.contains("navigatePreservingContext"));
        assertTrue(javascript.contains("new URL(window.location.href)"));
        assertFalse(javascript.contains("window.location.search ="));
        assertTrue(jsp.contains("var=\"itensInstaladosTotal\""));
        assertTrue(jsp.contains("RN BETWEEN ${baseStart} AND ${baseEnd}"));
        assertTrue(jsp.contains("dashboardView == 'installed'"));
        assertTrue(css.contains(".subtabs"));
        assertTrue(viewExposesCity());
        assertFalse(jsp.contains("fetch("));
        assertFalse(javascript.contains("fetch("));
    }

    @Test void pacoteJspIncorporaCssEJavascript() throws Exception {
        String build = read("build.gradle");
        assertTrue(build.contains("prepareGenesisDashboard"));
        assertTrue(build.contains("<style type=\"text/css\">"));
        assertTrue(build.contains("<script type=\"text/javascript\">"));
        assertTrue(build.contains("from(generatedGenesisDashboard)"));
    }

    @Test void viewAlteraSomenteTipoEstoquePeloCnpj() throws Exception {
        String view = read("sql/genesis/view/01_ad_vw_genesis_estoque.sql");
        assertTrue(view.contains("NVL(ESE.QTDENTRADAS, 0) > 0"));
        assertTrue(view.contains("NVL(EST.CODPARC, 0) = 0"));
        assertTrue(view.contains("END AS TIPO_ESTOQUE"));
        assertTrue(view.contains("THEN 'PROPRIO'"));
        assertTrue(view.contains("ELSE 'TERCEIRO'"));
        assertFalse(view.contains("END AS PODER_ESTOQUE"));
        assertTrue(view.contains("FROM TGFEST\n    GROUP BY CODEMP, CODPROD, CODLOCAL, TO_CHAR(CONTROLE)"));
        assertTrue(view.contains("FROM TGFESE\n    GROUP BY CODEMP, CODPROD, CODLOCALORIG, TO_CHAR(CONTROLE), CODTIPOPER"));
        assertTrue(view.contains("AND SER.SERIE IS NOT NULL"));
    }

    private boolean viewExposesCity() throws Exception {
        return read("sql/genesis/view/01_ad_vw_genesis_estoque.sql").contains("CID.NOMECID AS CIDADE");
    }

    @Test void integracaoAceitaSomenteEquipamentosM1() throws Exception {
        String view = read("sql/genesis/view/01_ad_vw_genesis_estoque.sql");
        String candidatos = read("src/br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.java");
        String service = read("src/br/com/zaya/mahospitalar/genesis/service/GenesisSyncService.java");
        String rollback = read("sql/genesis/view/03_rollback_filtro_tipo_produto.sql");
        assertTrue(view.contains("UPPER(COALESCE(TRIM(PRO.HSP_TIPOPRODUTO), TRIM(PRO.AD_TIPOPRODUTO))) = 'M1'"));
        assertTrue(candidatos.contains("UPPER(COALESCE(TRIM(PREGRA.HSP_TIPOPRODUTO),TRIM(PREGRA.AD_TIPOPRODUTO)))='M1'"));
        assertTrue(candidatos.contains("COALESCE(TRIM(PREGRA.HSP_TIPOPRODUTO),TRIM(PREGRA.AD_TIPOPRODUTO)) AS TIPO_PRODUTO"));
        assertTrue(service.contains("validarTipoProduto(candidato)"));
        assertTrue(service.contains("usando AD_TIPOPRODUTO somente quando HSP estiver vazio"));
        assertFalse(rollback.contains("UPPER(TRIM(PRO.AD_TIPOPRODUTO)) = 'M1'"));
    }

    @Test void conciliacaoSetoresPossuiBackupValidacaoERollback() throws Exception {
        String diagnostico = read("sql/genesis/conciliacao-setores/01_diagnosticar_setores.sql");
        String backup = read("sql/genesis/conciliacao-setores/02_backup_setores.sql");
        String merge = read("sql/genesis/conciliacao-setores/03_conciliar_setores.sql");
        String validacao = read("sql/genesis/conciliacao-setores/04_validar_conciliacao.sql");
        String rollback = read("sql/genesis/conciliacao-setores/05_rollback_conciliacao.sql");
        assertTrue(diagnostico.contains("CORRESPONDENCIA_NOME_UNICA"));
        assertTrue(backup.contains("CREATE TABLE ZYB_GSETLNK_20260910 AS"));
        assertTrue(merge.contains("MERGE INTO AD_GENSECTOR"));
        assertTrue(merge.contains("NVL(DESTINO.STATUSCONC, 'PENDENTE') = 'PENDENTE'"));
        assertTrue(merge.contains("DESTINO.PADRAO"));
        assertTrue(validacao.contains("SETORES_CONCILIADOS_INCOMPLETOS"));
        assertTrue(rollback.contains("USING ZYB_GSETLNK_20260910 BACKUP"));
        String dml = merge.toUpperCase();
        assertFalse(dml.contains("UPDATE TGFPRO"));
        assertFalse(dml.contains("UPDATE TGFPAR"));
        assertFalse(dml.contains("UPDATE TGFCAB"));
        assertFalse(dml.contains("UPDATE TGFITE"));
    }

    @Test void acaoAgendadaPossuiRastreamentoNoLogDoSistema() throws Exception {
        String action = read("src/br/com/zaya/mahospitalar/genesis/schedule/IntegracaoGenesisScheduleAction.java");
        String runner = read("src/br/com/zaya/mahospitalar/genesis/service/IntegracaoGenesisRunner.java");
        assertTrue(action.contains("[MAH-GENESIS]"));
        assertTrue(action.contains("System.out.println"));
        assertTrue(action.contains("GenesisSanitizer.sanitizeForLog(trace.toString())"));
        assertTrue(runner.contains("Consulta concluida. candidatos="));
        assertTrue(runner.contains("Controle PENDENTE preparado"));
        String candidatos = read("src/br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.java");
        assertTrue(candidatos.contains("PARTITION BY B.NUNOTA,UPPER(TRIM(B.SERIE))"));
        assertTrue(candidatos.contains("WHERE MX.RN_MOV=1"));
        String reprocessamento = read("src/br/com/zaya/mahospitalar/genesis/action/AcaoReprocessarGenesis.java");
        assertTrue(reprocessamento.contains("linha.save()"));
        assertTrue(reprocessamento.contains("\"TENTATIVAS\", BigDecimal.ZERO"));
        assertTrue(reprocessamento.contains("\"USU_REPROCESSAMENTO\", usuario"));
        assertFalse(reprocessamento.contains("reprocessarSelecionados"));
        assertFalse(reprocessamento.contains("getJdbcWrapper"));
        assertFalse(reprocessamento.contains("openSession"));
        assertFalse(reprocessamento.contains("closeSession"));
        assertFalse(reprocessamento.contains("setAutoCommit"));
        assertFalse(reprocessamento.contains(".commit()"));
        assertFalse(reprocessamento.contains(".rollback()"));
        assertFalse(action.contains("setAutoCommit"));
        assertFalse(action.contains(".commit()"));
        assertFalse(action.contains(".rollback()"));
        assertFalse(runner.contains(".commit()"));
        assertFalse(runner.contains(".rollback()"));
        assertFalse(action.contains("getToken()"));
    }

    @Test void homologacaoNaoDependeDePropriedadesDaJvm() throws Exception {
        String config = read("src/br/com/zaya/mahospitalar/genesis/repository/ConfigGenesisRepository.java");
        String runner = read("src/br/com/zaya/mahospitalar/genesis/service/IntegracaoGenesisRunner.java");
        assertTrue(config.contains("TOKENGENESIS"));
        assertTrue(config.contains("SELECT URLGENESIS FROM AD_CENTPARAM WHERE NUPAR=?"));
        assertFalse(config.contains("BASE_URL_HOMOLOGACAO"));
        assertFalse(config.contains("System.getProperty"));
        assertTrue(runner.contains("carregarTokenHomologacao"));
        assertFalse(runner.contains("EnvironmentGenesisSecretProvider"));
    }

    @Test void setorNaoMisturaParceirosNemImpõePadrao() throws Exception {
        String fonte = read("src/br/com/zaya/mahospitalar/genesis/repository/CadastroGenesisRepository.java");
        assertTrue(fonte.contains("CODPARC IS NULL AND ? IS NOT NULL AND IDCLIENTEGEN=?"));
        assertTrue(fonte.contains("AND IDREGSET=?"));
        assertTrue(fonte.contains("AND (CODPARC=? OR CODPARC IS NULL)"));
        assertFalse(fonte.contains("(CODPARC=? OR IDCLIENTEGEN=?)"));
        assertFalse(fonte.contains("CODPARC=?,PADRAO='S'"));
        assertTrue(fonte.contains("'Ativo',?,'N','CONCILIADO'"));
    }

    @Test void selecaoSemLimitesPreservaElegibilidade() throws Exception {
        String fonte = read("src/br/com/zaya/mahospitalar/genesis/repository/CandidatoGenesisRepository.java");
        assertTrue(fonte.contains("ROWNUM<=?"));
        assertFalse(fonte.contains("NVL(M.TENTATIVAS,0)<?"));
        assertFalse(fonte.contains("ps.setInt(1,maxTentativas)"));
        assertTrue(fonte.contains("('PENDENTE','REPROCESSAR','ERRO','REVISAO','SIMULADO')"));
        assertTrue(fonte.contains("B.SERIE IS NOT NULL"));
        assertTrue(fonte.contains("UPPER(COALESCE(TRIM(PREGRA.HSP_TIPOPRODUTO),TRIM(PREGRA.AD_TIPOPRODUTO)))='M1'"));
        assertTrue(fonte.contains("WHERE MX.RN_MOV=1"));
    }

    @Test void conciliacaoDeModeloBloqueiaIdGenesisDuplicado() throws Exception {
        String fonte = read("src/br/com/zaya/mahospitalar/genesis/repository/CadastroGenesisRepository.java");
        String baseInstalada = read("src/br/com/zaya/mahospitalar/genesis/repository/BaseInstaladaGenesisRepository.java");
        assertTrue(fonte.contains("validarIdModeloDisponivel(jdbc, idGenesis, c.codProd, null)"));
        assertTrue(fonte.contains("validarIdModeloDisponivel(jdbc, idGenesis, null, local.getIdRegistro())"));
        assertTrue(fonte.contains("ID Genesis de modelo "));
        assertTrue(fonte.contains("WHERE IDBASE=? AND IDGENESIS=?"));
        assertTrue(baseInstalada.contains("validarIdModeloUnico(jdbc, idModelo, candidato.codProd)"));
        assertTrue(baseInstalada.contains("possui \" + quantidade + \" vinculos locais; envio bloqueado"));
    }

    private String read(String path) throws Exception { return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8); }
}
