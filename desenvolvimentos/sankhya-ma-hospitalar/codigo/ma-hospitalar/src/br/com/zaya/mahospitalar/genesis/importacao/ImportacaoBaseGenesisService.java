package br.com.zaya.mahospitalar.genesis.importacao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class ImportacaoBaseGenesisService {
    public static final int LIMITE_LOTE = 250;

    private final ObjectMapper objectMapper;
    private final MapeadorPlanilhaGenesis mapeador;
    private final BaseGenesisImportRepository repository;

    public ImportacaoBaseGenesisService() {
        this(new ObjectMapper(), new MapeadorPlanilhaGenesis(), new BaseGenesisImportRepository());
    }

    ImportacaoBaseGenesisService(ObjectMapper objectMapper, MapeadorPlanilhaGenesis mapeador,
                                 BaseGenesisImportRepository repository) {
        this.objectMapper = objectMapper;
        this.mapeador = mapeador;
        this.repository = repository;
    }

    public ResultadoLoteGenesis importar(BigDecimal idBase, String nomeAba, String dadosJson,
                                         int linhaInicial) throws Exception {
        if (idBase == null || idBase.signum() <= 0) throw new IllegalArgumentException("IDBASE invalido.");
        AbaBaseGenesis aba = AbaBaseGenesis.porNome(nomeAba);
        JsonNode linhas = objectMapper.readTree(dadosJson == null ? "" : dadosJson);
        if (!linhas.isArray()) throw new IllegalArgumentException("O lote deve ser um array JSON.");
        if (linhas.size() == 0) return new ResultadoLoteGenesis(0, 0);
        if (linhas.size() > LIMITE_LOTE) {
            throw new IllegalArgumentException("Lote excede o limite de " + LIMITE_LOTE + " linhas.");
        }

        int inseridos = 0;
        int atualizados = 0;
        int numeroLinha = linhaInicial < 2 ? 2 : linhaInicial;
        Set<BigDecimal> idsNoLote = new HashSet<BigDecimal>();
        Iterator<JsonNode> iterator = linhas.elements();
        while (iterator.hasNext()) {
            int linhaAtual = numeroLinha++;
            try {
                RegistroBaseGenesis registro = mapeador.mapear(aba, iterator.next(), linhaAtual);
                if (!idsNoLote.add(registro.getIdGenesis())) {
                    throw new IllegalArgumentException("ID Genesis duplicado no lote: " + registro.getIdGenesis());
                }
                if (repository.salvar(idBase, registro)) inseridos++;
                else atualizados++;
            } catch (Exception e) {
                throw new IllegalArgumentException("Aba " + aba.getNomePlanilha() + ", linha " + linhaAtual
                        + ": " + mensagem(e), e);
            }
        }
        return new ResultadoLoteGenesis(inseridos, atualizados);
    }

    private String mensagem(Exception e) {
        String texto = e.getMessage();
        return texto == null || texto.trim().isEmpty() ? e.getClass().getSimpleName() : texto;
    }

    public void iniciar(BigDecimal idBase, String arquivo, BigDecimal usuario) throws Exception {
        repository.iniciar(idBase, limitarNomeArquivo(arquivo), usuario);
    }

    public void finalizar(BigDecimal idBase) throws Exception {
        repository.finalizar(idBase);
    }

    private String limitarNomeArquivo(String arquivo) {
        if (arquivo == null) return null;
        String nome = arquivo.replace('\\', '/');
        int separador = nome.lastIndexOf('/');
        if (separador >= 0) nome = nome.substring(separador + 1);
        if (nome.length() > 255) nome = nome.substring(nome.length() - 255);
        return nome;
    }
}
