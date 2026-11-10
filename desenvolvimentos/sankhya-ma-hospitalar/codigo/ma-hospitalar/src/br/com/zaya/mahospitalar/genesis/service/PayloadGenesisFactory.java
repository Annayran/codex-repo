package br.com.zaya.mahospitalar.genesis.service;

import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.domain.PayloadGenesis;
import br.com.zaya.mahospitalar.genesis.domain.TipoEntidade;
import br.com.zaya.mahospitalar.genesis.dto.*;
import br.com.zaya.mahospitalar.genesis.util.GenesisHash;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public final class PayloadGenesisFactory {
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    private final String unidadeEpoch;

    public PayloadGenesisFactory(String unidadeEpoch) { this.unidadeEpoch = unidadeEpoch; }

    public PayloadGenesis cliente(CandidatoGenesis c) throws Exception {
        ClienteGenesisDto dto = new ClienteGenesisDto();
        dto.codigoExterno = c.codigoExternoCliente;
        String documento = exigir(somenteDigitos(c.cnpjCpf), "CPF/CNPJ do cliente");
        if (documento.length() <= 11) dto.cpf = documento; else dto.cnpj = documento;
        dto.nomeFantasia = c.nomeParceiro;
        dto.razaoSocial = c.razaoParceiro;
        dto.status = ativo(c.parceiroAtivo);
        dto.email = c.email;
        dto.endereco = c.endereco;
        dto.enderecoBairro = c.bairro;
        dto.enderecoCidade = c.cidade;
        dto.enderecoUF = c.uf;
        dto.cep = somenteDigitos(c.cep);
        dto.telefone = somenteDigitos(c.telefone);
        return payload(TipoEntidade.CLIENTE, c.chaveCliente, dto.codigoExterno, dto);
    }

    public PayloadGenesis setor(CandidatoGenesis c, String idCliente) throws Exception {
        SetorGenesisDto dto = new SetorGenesisDto();
        dto.codigoExterno = c.codigoExternoSetor;
        dto.nome = primeiroPreenchido(c.razaoParceiro, c.nomeParceiro, "PADRAO");
        dto.idCliente = exigir(idCliente, "cliente");
        return payload(TipoEntidade.SETOR, c.chaveSetor, dto.codigoExterno, dto);
    }

    public PayloadGenesis material(CandidatoGenesis c) throws Exception {
        MaterialGenesisDto dto = new MaterialGenesisDto();
        dto.codigoExterno = c.codigoExternoMaterial;
        dto.nome = c.descricaoGrupo;
        dto.status = ativo(c.produtoAtivo);
        dto.grauRisco = c.grauRisco;
        return payload(TipoEntidade.MATERIAL, c.chaveMaterial, dto.codigoExterno, dto);
    }

    public PayloadGenesis fabricante(CandidatoGenesis c) throws Exception {
        FabricanteGenesisDto dto = new FabricanteGenesisDto();
        dto.codigoExterno = c.codigoExternoFabricante;
        dto.nomeFantasia = c.nomeFabricante;
        dto.razaoSocial = c.nomeFabricante;
        String documento = exigir(somenteDigitos(c.cnpjFabricante), "CNPJ do fabricante");
        if (documento.length() != 14) {
            throw new IllegalStateException("CNPJ do fabricante deve possuir 14 digitos para CODPROD=" +
                    c.codProd + ".");
        }
        dto.cnpj = documento;
        dto.status = "Ativo";
        dto.fabricante = true;
        dto.fornecedor = true;
        return payload(TipoEntidade.FABRICANTE, c.chaveFabricante, dto.codigoExterno, dto);
    }

    public PayloadGenesis modelo(CandidatoGenesis c, String idFabricante, String idMaterial) throws Exception {
        ModeloGenesisDto dto = new ModeloGenesisDto();
        dto.codigoExterno = exigir(c.codigoExternoModelo, "HSP_IDEXTERNO do modelo");
        dto.nome = c.descricaoProduto;
        dto.idEmpresaFabricante = exigir(idFabricante, "fabricante");
        dto.idMaterial = exigir(idMaterial, "material");
        dto.status = ativo(c.produtoAtivo);
        dto.nranvisa = c.codAnvisa;
        return payload(TipoEntidade.MODELO, c.chaveModelo, dto.codigoExterno, dto);
    }

    public PayloadGenesis fabricanteParaResolucao(CandidatoGenesis c) throws Exception {
        FabricanteGenesisDto dto = new FabricanteGenesisDto();
        dto.codigoExterno = c.codigoExternoFabricante;
        dto.nomeFantasia = c.nomeFabricante == null ? c.marca : c.nomeFabricante;
        dto.razaoSocial = dto.nomeFantasia;
        dto.cnpj = somenteDigitos(c.cnpjFabricante);
        dto.status = "Ativo"; dto.fabricante = true; dto.fornecedor = true;
        return payload(TipoEntidade.FABRICANTE, c.chaveFabricante, dto.codigoExterno, dto);
    }

    public PayloadGenesis item(CandidatoGenesis c, Map<TipoEntidade, String> ids) throws Exception {
        validarSituacaoItem(c);
        ItemMaterialGenesisDto dto = new ItemMaterialGenesisDto();
        dto.idCliente = idNumerico(ids.get(TipoEntidade.CLIENTE), "cliente");
        dto.idSetor = idNumerico(ids.get(TipoEntidade.SETOR), "setor");
        dto.idModelo = idNumerico(ids.get(TipoEntidade.MODELO), "modelo");
        dto.nserie = normalizarSerie(c.serie);
        dto.dataAquisicao = epoch(c.dataMovimento);
        dto.dataInstalacao = epoch(c.dataMovimento);
        dto.ultimaInstalacao = epoch(c.dataMovimento);
        dto.valorUnitario = c.valorUnitario;
        dto.ultimoPreco = c.valorUnitario;
        dto.matQuantidade = BigDecimal.ONE;
        dto.valorTotal = c.valorUnitario == null ? null : c.valorUnitario.multiply(dto.matQuantidade);
        dto.situacao = c.situacaoGenesis;
        dto.idSituacaoEquipamento = c.idSituacaoGenesis;
        dto.status = "Ativo";
        dto.garantia = c.garantiaMeses;
        dto.marca = c.nomeFabricante == null ? c.marca : c.nomeFabricante;
        dto.codigoExterno = c.codigoExternoItem;
        dto.nnotaFiscal = inteiro(c.numNota);
        dto.dono = c.poder;
        dto.localizacao = c.localizacao;
        dto.dadosTransicao = c.tipoEvento;
        dto.observacao = c.chaveMovimento == null ? null : "Movimento Sankhya: " + c.chaveMovimento;
        dto.outros = "Origem operacional: Sankhya; movimento: " + c.tipoMovimento;
        String chaveItem = c.codProd == null ? c.chaveItem : c.codProd.toPlainString() + "|" + dto.nserie;
        return payload(TipoEntidade.ITEM_MATERIAL, chaveItem, dto.codigoExterno, dto);
    }

    public static void validarSituacaoItem(CandidatoGenesis c) {
        if (c.statusEstoque == null || c.statusEstoque.trim().isEmpty()
                || "O".equalsIgnoreCase(c.statusEstoque.trim())) {
            // Default approved for outbound payload only; never write back to the source series.
            c.statusEstoque = "O";
            c.statusEstoqueDescricao = "Operacional";
            c.situacaoGenesis = "Operacional";
            c.idSituacaoGenesis = 1L;
        }
        if (c.situacaoGenesis == null || c.situacaoGenesis.trim().isEmpty()) {
            throw new IllegalStateException("SITUACAO_ORIGEM_AUSENTE: preencher uma situacao reconhecida " +
                    "na serie do item da nota; codigo preenchido nao reconhecido, sem substituicao automatica.");
        }
        if (c.idSituacaoGenesis == null || c.idSituacaoGenesis <= 0) {
            throw new IllegalStateException("SITUACAO_NAO_CONCILIADA: conciliar um unico dominio ativo de " +
                    "situacao com o codigo da serie na Base instalada antes de enviar; nenhum ID foi presumido.");
        }
    }

    private PayloadGenesis payload(TipoEntidade tipo, String chave, String codigoExterno, Object dto) throws Exception {
        String json = mapper.writeValueAsString(dto);
        return new PayloadGenesis(tipo, exigir(chave, tipo.name()), exigir(codigoExterno, "codigoExterno"), dto, json, GenesisHash.sha256(json));
    }

    public PayloadGenesis preservarCodigoExterno(PayloadGenesis payload, String codigoExterno) throws Exception {
        if (payload.getTipo() == TipoEntidade.MODELO) {
            if (codigoExterno != null && !codigoExterno.trim().isEmpty() && !codigoExterno.trim().equals(payload.getCodigoExterno()))
                throw new IllegalStateException("Codigo do modelo difere de HSP_IDEXTERNO; migrar/reconciliar por ID antes de continuar, sem novo POST.");
            return payload;
        }
        if (codigoExterno == null || codigoExterno.trim().isEmpty() || codigoExterno.equals(payload.getCodigoExterno())) return payload;
        payload.getDto().getClass().getField("codigoExterno").set(payload.getDto(), codigoExterno);
        return payload(payload.getTipo(), payload.getChaveSankhya(), codigoExterno, payload.getDto());
    }

    private Long epoch(Date date) {
        if (date == null) return null;
        long ms = date.getTime();
        if ("S".equals(unidadeEpoch)) return ms / 1000L;
        if ("US".equals(unidadeEpoch)) return ms * 1000L;
        if ("MS".equals(unidadeEpoch)) return ms;
        throw new IllegalStateException("Unidade de epoch nao confirmada.");
    }

    private static String ativo(String value) {
        return "N".equalsIgnoreCase(value) || "INATIVO".equalsIgnoreCase(value)
                ? "Inativo" : "Ativo";
    }
    private static String primeiroPreenchido(String primeiro, String segundo, String fallback) {
        if (primeiro != null && !primeiro.trim().isEmpty()) return primeiro.trim();
        if (segundo != null && !segundo.trim().isEmpty()) return segundo.trim();
        return fallback;
    }
    private static String somenteDigitos(String value) { return value == null ? null : value.replaceAll("\\D", ""); }
    public static String normalizarSerie(String serie) { return serie == null ? null : serie.trim().toUpperCase(); }
    private static String exigir(String value, String nome) {
        if (value == null || value.trim().isEmpty()) throw new IllegalStateException("Dependencia/campo obrigatorio ausente: " + nome);
        return value.trim();
    }

    private static Long idNumerico(String value, String nome) {
        String id = exigir(value, nome);
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("ID Genesis deve ser numerico para " + nome + ": " + id, e);
        }
    }

    private static Long inteiro(java.math.BigDecimal value) {
        return value == null ? null : value.longValueExact();
    }
}
