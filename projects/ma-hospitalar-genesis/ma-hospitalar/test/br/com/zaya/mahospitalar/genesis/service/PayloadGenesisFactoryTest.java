package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.domain.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PayloadGenesisFactoryTest {
    @Test void mesmaNotaComSeriesDiferentesGeraControlesIndependentes() throws Exception {
        CandidatoGenesis a = candidato();
        CandidatoGenesis b = candidato();
        a.nuNota = b.nuNota = new BigDecimal("114375");
        a.serie = "6252605000214";
        b.serie = "6252605000254";
        a.codigoExternoItem = "SNK-ITEM-30-" + a.serie;
        b.codigoExternoItem = "SNK-ITEM-30-" + b.serie;
        a.chaveMovimento = "2|114375|1|30|" + a.serie;
        b.chaveMovimento = "2|114375|1|30|" + b.serie;
        Map<TipoEntidade,String> ids = new EnumMap<>(TipoEntidade.class);
        ids.put(TipoEntidade.CLIENTE,"101");
        ids.put(TipoEntidade.SETOR,"201");
        ids.put(TipoEntidade.MODELO,"501");
        PayloadGenesisFactory factory = new PayloadGenesisFactory("MS");
        PayloadGenesis primeiro = factory.item(a,ids);
        PayloadGenesis segundo = factory.item(b,ids);
        assertEquals("30|" + a.serie,primeiro.getChaveSankhya());
        assertEquals("30|" + b.serie,segundo.getChaveSankhya());
        assertNotEquals(primeiro.getChaveSankhya(),segundo.getChaveSankhya());
        assertNotEquals(primeiro.getCodigoExterno(),segundo.getCodigoExterno());
        assertNotEquals(primeiro.getHash(),segundo.getHash());
        assertTrue(primeiro.getJson().contains("\"nnotaFiscal\":9876"));
        assertTrue(segundo.getJson().contains("\"nnotaFiscal\":9876"));
    }
    @Test void modeloUsaCodigoHspSemGerarPrefixo() throws Exception {
        CandidatoGenesis c=candidato();c.codigoExternoModelo=" 000MC-EP90IMB ";
        PayloadGenesis p=new PayloadGenesisFactory("MS").modelo(c,"4","311");
        assertEquals("000MC-EP90IMB",p.getCodigoExterno());assertTrue(p.getJson().contains("\"codigoExterno\":\"000MC-EP90IMB\""));
        assertEquals("30",p.getChaveSankhya());
    }
    @Test void modeloSemHspNaoUsaCodigoProdutoComoFallback() {
        CandidatoGenesis c=candidato();c.codigoExternoModelo=null;
        assertThrows(IllegalStateException.class,()->new PayloadGenesisFactory("MS").modelo(c,"4","311"));
    }
    @Test void modeloNaoPreservaCodigoSnkAntigo() throws Exception {
        CandidatoGenesis c=candidato();c.codigoExternoModelo="MC-EP90IMB";
        PayloadGenesisFactory f=new PayloadGenesisFactory("MS");PayloadGenesis p=f.modelo(c,"4","311");
        assertThrows(IllegalStateException.class,()->f.preservarCodigoExterno(p,"SNK-MOD-30"));
        assertEquals("MC-EP90IMB",p.getCodigoExterno());
    }
    @Test void modeloComCodigoHspJaConciliadoPermaneceIgual() throws Exception {
        CandidatoGenesis c=candidato();c.codigoExternoModelo="MC-EP90";
        PayloadGenesisFactory f=new PayloadGenesisFactory("MS");PayloadGenesis p=f.modelo(c,"4","311");
        assertSame(p,f.preservarCodigoExterno(p,"MC-EP90"));
    }
    @Test
    void geraArvoreComChavesHashEIdentidadePorSerie() throws Exception {
        CandidatoGenesis c = candidato();
        PayloadGenesisFactory factory = new PayloadGenesisFactory("MS");
        PayloadGenesis cliente = factory.cliente(c);
        PayloadGenesis setor = factory.setor(c, "101");
        PayloadGenesis material = factory.material(c);
        PayloadGenesis fabricante = factory.fabricante(c);
        PayloadGenesis modelo = factory.modelo(c, "401", "301");
        Map<TipoEntidade,String> ids = new EnumMap<>(TipoEntidade.class);
        ids.put(TipoEntidade.CLIENTE,"101"); ids.put(TipoEntidade.SETOR,"201");
        ids.put(TipoEntidade.MATERIAL,"301"); ids.put(TipoEntidade.MODELO,"501");
        PayloadGenesis item = factory.item(c, ids);

        assertEquals("10", cliente.getChaveSankhya());
        assertEquals("10|PADRAO", setor.getChaveSankhya());
        assertEquals("20", material.getChaveSankhya());
        assertEquals("FAB01", fabricante.getChaveSankhya());
        assertTrue(fabricante.getJson().contains("\"cnpj\":\"12345678000190\""));
        assertTrue(setor.getJson().contains("\"nome\":\"Cliente SA\""));
        assertEquals("30", modelo.getChaveSankhya());
        assertEquals("30|AB-123", item.getChaveSankhya());
        assertTrue(item.getJson().contains("\"nserie\":\"AB-123\""));
        assertTrue(item.getJson().contains("\"idCliente\":101"));
        assertTrue(item.getJson().contains("\"nnotaFiscal\":9876"));
        assertTrue(item.getJson().contains("\"dono\":\"PROPRIO\""));
        assertTrue(item.getJson().contains("\"status\":\"Operacional\""));
        assertEquals(64, item.getHash().length());
        assertEquals(item.getHash(), factory.item(c, ids).getHash());
    }

    @Test
    void preservaCodigoExternoLegadoNoPut() throws Exception {
        PayloadGenesisFactory factory = new PayloadGenesisFactory("S");
        PayloadGenesis novo = factory.cliente(candidato());
        PayloadGenesis legado = factory.preservarCodigoExterno(novo, "PROTHEUS-0001");
        assertEquals("PROTHEUS-0001", legado.getCodigoExterno());
        assertTrue(legado.getJson().contains("PROTHEUS-0001"));
        assertNotEquals(novo.getHash(), legado.getHash());
    }

    @Test
    void recusaDependenciaAusenteSemDefaultSilencioso() {
        PayloadGenesisFactory factory = new PayloadGenesisFactory("MS");
        assertThrows(IllegalStateException.class, () -> factory.setor(candidato(), null));
    }

    @Test
    void recusaDocumentoDeClienteAusenteComErroFuncionalClaro() {
        CandidatoGenesis c = candidato();
        c.cnpjCpf = null;
        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> new PayloadGenesisFactory("MS").cliente(c));
        assertTrue(erro.getMessage().contains("CPF/CNPJ"));
    }

    @Test
    void recusaFabricanteSemCnpjValidoAntesDoHttp() {
        CandidatoGenesis c = candidato();
        c.cnpjFabricante = null;
        IllegalStateException erro = assertThrows(IllegalStateException.class,
                () -> new PayloadGenesisFactory("MS").fabricante(c));
        assertTrue(erro.getMessage().contains("CNPJ do fabricante"));
    }

    @Test
    void respeitaStatusInativoTextual() throws Exception {
        CandidatoGenesis c = candidato();
        c.parceiroAtivo = "Inativo";
        assertTrue(new PayloadGenesisFactory("MS").cliente(c).getJson()
                .contains("\"status\":\"Inativo\""));
    }

    private CandidatoGenesis candidato() {
        CandidatoGenesis c = new CandidatoGenesis();
        c.codParc = BigDecimal.TEN; c.codGrupoProd = new BigDecimal("20"); c.codProd = new BigDecimal("30");
        c.numNota = new BigDecimal("9876"); c.sequencia = BigDecimal.ONE;
        c.dataMovimento = new Date(1700000000000L); c.valorUnitario = new BigDecimal("125.50");
        c.quantidade = BigDecimal.ONE;
        c.tipoEvento = "REMESSA"; c.situacaoGenesis = "Instalado"; c.mudaOrigem = true;
        c.descricaoProduto = "Monitor"; c.descricaoGrupo = "Equipamento"; c.serie = " ab-123 "; c.garantiaMeses = 12;
        c.statusEstoque = "O"; c.statusEstoqueDescricao = "Operacional";
        c.tipoMovimento = "SAIDA"; c.poder = "PROPRIO"; c.localizacao = "Cliente";
        c.chaveMovimento = "1|100|1|30|AB-123"; c.idSituacaoGenesis = 77L;
        c.nomeParceiro = "Cliente"; c.razaoParceiro = "Cliente SA"; c.cnpjCpf = "12.345.678/0001-90";
        c.produtoAtivo = "S"; c.parceiroAtivo = "S"; c.grauRisco = "Medio";
        c.codigoFabricante = "FAB01"; c.nomeFabricante = "Fabricante";
        c.cnpjFabricante = "12.345.678/0001-90";
        c.chaveCliente="10"; c.chaveSetor="10|PADRAO"; c.chaveMaterial="20"; c.chaveFabricante="FAB01";
        c.chaveModelo="30"; c.chaveItem="30| ab-123 ";
        c.codigoExternoCliente="SNK-CLI-10"; c.codigoExternoSetor="SNK-SET-10";
        c.codigoExternoMaterial="SNK-MAT-20"; c.codigoExternoFabricante="SNK-FAB-FAB01";
        c.codigoExternoModelo="SNK-MOD-30"; c.codigoExternoItem="SNK-ITEM-30-AB-123";
        return c;
    }
}
