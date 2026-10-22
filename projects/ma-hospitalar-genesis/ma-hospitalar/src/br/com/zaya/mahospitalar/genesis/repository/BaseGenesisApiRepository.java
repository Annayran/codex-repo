package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.client.GenesisApiResult;
import br.com.zaya.mahospitalar.genesis.domain.*;
import br.com.zaya.mahospitalar.genesis.service.GenesisSyncService;
import com.fasterxml.jackson.databind.JsonNode;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public final class BaseGenesisApiRepository {
    public void persistir(JdbcWrapper jdbc, CandidatoGenesis c, ContextoBaseInstaladaGenesis locais,
                           GenesisSyncService.Resultado r, String unidadeEpoch) throws Exception {
        long id = Long.parseLong(r.api.getIdGenesis());
        if (id <= 0) throw new IllegalStateException("ID remoto invalido para persistencia.");
        String codigo = r.payload.getCodigoExterno();
        CadastroGenesisRepository repo = new CadastroGenesisRepository();
        ReferenciaGenesisLocal existente;
        switch (r.payload.getTipo()) {
            case CLIENTE: existente = locais.cliente; break;
            case SETOR: existente = locais.setor; break;
            case MATERIAL: existente = locais.material; break;
            case FABRICANTE: existente = locais.fabricante; break;
            case MODELO: existente = locais.modelo; break;
            case ITEM_MATERIAL: existente = locais.itemMaterial; break;
            default: throw new IllegalArgumentException("Entidade fora da base instalada.");
        }
        if (existente.getIdGenesis() != null && r.operacao == GenesisSyncService.Operacao.SEM_ALTERACAO) {
            vincular(jdbc, c, r.payload.getTipo(), existente, id);
            atualizarRetorno(jdbc, r.payload.getTipo(), id, r.api, unidadeEpoch);
            return;
        }
        switch (r.payload.getTipo()) {
            case CLIENTE: repo.registrarCliente(jdbc, c, id, codigo); break;
            case SETOR: repo.registrarSetor(jdbc, c, id, locais.cliente.getIdGenesis(), codigo, locais.setor); break;
            case MATERIAL: repo.registrarMaterial(jdbc, c, id, codigo); break;
            case FABRICANTE: repo.registrarFabricante(jdbc, c, id, codigo, locais.fabricante); break;
            case MODELO: repo.registrarModelo(jdbc, c, id, locais.fabricante.getIdGenesis(),
                    locais.material.getIdGenesis(), codigo); break;
            case ITEM_MATERIAL:
                new BaseInstaladaGenesisRepository().registrarResultadoItem(jdbc, c,
                        new ReferenciasBaseInstaladaGenesis(locais.cliente.getIdGenesis(),
                                locais.setor.getIdGenesis(), locais.modelo.getIdGenesis(),
                                locais.itemMaterial.getIdGenesis(), codigo), r.api.getIdGenesis(), codigo);
                break;
            default: throw new IllegalArgumentException("Entidade fora da base instalada.");
        }
        atualizarRetorno(jdbc, r.payload.getTipo(), id, r.api, unidadeEpoch);
    }

    private void vincular(JdbcWrapper jdbc, CandidatoGenesis c, TipoEntidade tipo,
                           ReferenciaGenesisLocal local, long id) throws Exception {
        if (tipo == TipoEntidade.MATERIAL && local.isHerdadaDoModelo()) {
            validarMaterialModelo(jdbc.getConnection(),c,local,id);
            return;
        }
        String tabela, pk, campo; Object valor;
        switch (tipo) {
            case CLIENTE: tabela="AD_GENCLIENTE"; pk="IDREGCLI"; campo="CODPARC"; valor=c.codParc; break;
            case SETOR: tabela="AD_GENSECTOR"; pk="IDREGSET"; campo="CODPARC"; valor=c.codParc; break;
            case MATERIAL: tabela="AD_GENMATERIAL"; pk="IDREGMAT"; campo="CODGRUPOPROD"; valor=c.codGrupoProd; break;
            case MODELO: tabela="AD_GENMODELO"; pk="IDREGMOD"; campo="CODPROD"; valor=c.codProd; break;
            case FABRICANTE:
                try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                        "UPDATE AD_GENEMPRESA SET MARCASANKHYA=COALESCE(MARCASANKHYA,?) " +
                                "WHERE IDBASE=1 AND IDREGEMP=? AND IDGENESIS=?")) {
                    ps.setString(1, c.marca); ps.setLong(2, local.getIdRegistro()); ps.setLong(3, id);
                    if (ps.executeUpdate()!=1) throw new IllegalStateException("Vinculo de fabricante ambiguo.");
                }
                return;
            default: return;
        }
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE " + tabela + " SET " + campo + "=? WHERE IDBASE=1 AND " + pk +
                        "=? AND IDGENESIS=? AND (" + campo + " IS NULL OR " + campo + "=?)")) {
            ps.setObject(1, valor); ps.setLong(2, local.getIdRegistro()); ps.setLong(3, id); ps.setObject(4, valor);
            if (ps.executeUpdate()!=1) throw new IllegalStateException("Vinculo local pertence a outro cadastro ou foi alterado: entidade="+
                    tipo+", IDBASE=1, "+pk+"="+local.getIdRegistro()+", IDGENESIS="+id+", "+campo+" esperado="+valor+". Nenhum vinculo substituido.");
        }
    }

    void validarMaterialModelo(java.sql.Connection connection, CandidatoGenesis c,
                               ReferenciaGenesisLocal local, long id) throws Exception {
        if (local.getIdGenesis()==null || local.getIdGenesis().longValue()!=id)
            throw new IllegalStateException("ID do material diverge do vinculo herdado do modelo.");
        // Material may serve several product groups; do not overwrite its reconciled group.
        try (PreparedStatement ps=connection.prepareStatement(
                "SELECT COUNT(*) FROM AD_GENMATERIAL E JOIN AD_GENMODELO M " +
                "ON M.IDBASE=E.IDBASE AND M.IDMATERIALGEN=E.IDGENESIS " +
                "WHERE E.IDBASE=1 AND E.IDREGMAT=? AND E.IDGENESIS=? AND M.CODPROD=?")) {
            ps.setLong(1,local.getIdRegistro()); ps.setLong(2,id); ps.setBigDecimal(3,c.codProd);
            try (java.sql.ResultSet rs=ps.executeQuery()) {
                if (!rs.next() || rs.getInt(1)!=1)
                    throw new IllegalStateException("Material/modelo alterado ou ambiguo; vinculo preservado sem sobrescrita.");
            }
        }
    }

    public void atualizarRetorno(JdbcWrapper jdbc, TipoEntidade tipo, long id, GenesisApiResult api,
                                  String unidadeEpoch) throws Exception {
        atualizarRetorno(jdbc.getConnection(), tipo, id, api, unidadeEpoch);
    }

    void atualizarRetorno(java.sql.Connection connection, TipoEntidade tipo, long id, GenesisApiResult api,
                           String unidadeEpoch) throws Exception {
        JsonNode dados = api.registroParaPersistencia();
        if (dados == null) return;
        String tabela;
        String campos;
        switch (tipo) {
            case CLIENTE:
                tabela = "AD_GENCLIENTE";
                campos = "codigoExterno:CODIGOEXTERNO:200,nomeFantasia:NOMEFANTASIA:255,razaoSocial:RAZAOSOCIAL:255," +
                        "cnpj:CNPJ:20,cpf:CPF:14,status:STATUSGENESIS:20,email:EMAIL:255,telefone:TELEFONE:30," +
                        "cep:CEP:10,endereco:ENDERECO:255,enderecoBairro:ENDERECOBAIRRO:120," +
                        "enderecoCidade:ENDERECOCIDADE:120,enderecoUF:ENDERECOUF:2,enderecoNumero:ENDERECONUMERO:30";
                break;
            case SETOR: tabela = "AD_GENSECTOR";
                campos = "codigoExterno:CODIGOEXTERNO:200,nome:NOME:255,idCliente:IDCLIENTEGEN:N,status:STATUSGENESIS:20"; break;
            case MATERIAL: tabela = "AD_GENMATERIAL";
                campos = "codigoExterno:CODIGOEXTERNO:200,nome:NOME:255,tipo:TIPO:30,grauRisco:GRAURISCO:30,status:STATUSGENESIS:20"; break;
            case FABRICANTE: tabela = "AD_GENEMPRESA";
                campos = "codigoExterno:CODIGOEXTERNO:200,nomeFantasia:NOMEFANTASIA:255,razaoSocial:RAZAOSOCIAL:255," +
                        "status:STATUSGENESIS:20,fabricante:FABRICANTE:B,fornecedor:FORNECEDOR:B"; break;
            case MODELO: tabela = "AD_GENMODELO";
                campos = "codigoExterno:CODIGOEXTERNO:200,nome:NOME:255,idEmpresaFabricante:IDEMPRESAGEN:N," +
                        "idMaterial:IDMATERIALGEN:N,status:STATUSGENESIS:20,nranvisa:NRANVISA:50"; break;
            case ITEM_MATERIAL: tabela = "AD_GENITEMMAT";
                campos = "codigoExterno:CODIGOEXTERNO:200,nserie:NSERIE:150,idModelo:IDMODELOGEN:N," +
                        "idCliente:IDCLIENTEGEN:N,idSetor:IDSETORGEN:N,status:STATUSGENESIS:20," +
                        "valorUnitario:VALORUNITARIO:N,dataAquisicao:DATAAQUISICAO:D,dataInstalacao:DATAINSTALACAO:D," +
                        "ultimaInstalacao:ULTIMAINSTALACAO:D,marca:MARCA:100,garantia:GARANTIA:N,dono:DONO:50,situacao:SITUACAO:50," +
                        "rm:RM:100,outros:OUTROS:255,idPropriedadeEquipamento:IDPROPRIEDADEGEN:N,idSituacaoEquipamento:IDSITUACAOGEN:N"; break;
            default: throw new IllegalArgumentException("Entidade nao suportada.");
        }
        List<Object> valores = new ArrayList<Object>();
        List<String> colunas = new ArrayList<String>();
        StringBuilder sets = new StringBuilder();
        for (String campo : campos.split(",")) {
            String[] partes = campo.split(":");
            if (!dados.has(partes[0])) continue;
            JsonNode valor = dados.get(partes[0]);
            Object convertido = null;
            if (!valor.isNull()) {
                if ("N".equals(partes[2])) convertido = new java.math.BigDecimal(valor.asText());
                else if ("B".equals(partes[2])) convertido = valor.isBoolean() ?
                        (valor.asBoolean() ? 1 : 0) : Integer.valueOf(valor.asText());
                else if ("D".equals(partes[2])) {
                    long epoch = Long.parseLong(valor.asText());
                    long ms = "S".equals(unidadeEpoch) ? Math.multiplyExact(epoch, 1000L) :
                            "US".equals(unidadeEpoch) ? epoch / 1000 : epoch;
                    convertido = new Timestamp(ms);
                } else {
                    String texto = valor.asText();
                    int max = Integer.parseInt(partes[2]);
                    convertido = texto.length() > max ? texto.substring(0, max) : texto;
                }
            }
            if (sets.length() > 0) sets.append(",");
            sets.append(partes[1]).append("=?");
            valores.add(convertido);
            colunas.add(partes[1]);
        }
        if (sets.length() == 0) return;
        List<String> alterados = new ArrayList<String>();
        if (api.getIdRastreio() != null) {
            try (PreparedStatement antes = connection.prepareStatement(
                    "SELECT " + String.join(",",colunas) + " FROM " + tabela + " WHERE IDBASE=1 AND IDGENESIS=?")) {
                antes.setLong(1,id);
                try (java.sql.ResultSet rs = antes.executeQuery()) {
                    if (!rs.next()) throw new IllegalStateException("Registro local ausente antes da aplicacao.");
                    for (int j=0;j<colunas.size();j++) {
                        Object antigo=rs.getObject(j+1), novo=valores.get(j);
                        boolean igual=antigo instanceof Number && novo instanceof Number
                                ? new java.math.BigDecimal(antigo.toString()).compareTo(new java.math.BigDecimal(novo.toString()))==0
                                : java.util.Objects.equals(antigo,novo);
                        if(!igual)alterados.add(colunas.get(j));
                    }
                    if(rs.next())throw new IllegalStateException("ID local duplicado antes da aplicacao.");
                }
            }
        }
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE " + tabela + " SET " + sets +
                        ",STATUSCONC='CONCILIADO',MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE WHERE IDBASE=1 AND IDGENESIS=?")) {
            int i = 1;
            for (Object valor : valores) ps.setObject(i++, valor);
            ps.setLong(i, id);
            if (ps.executeUpdate() != 1) throw new IllegalStateException("Retorno remoto ambiguo na base instalada; revisar ID.");
        }
        GenesisRequestRepository.persistido(connection, api, "IDBASE=1; entidade=" + tipo + "; id=" + id +
                "; campos aplicados=" + colunas + "; campos alterados=" + alterados);
    }
}
