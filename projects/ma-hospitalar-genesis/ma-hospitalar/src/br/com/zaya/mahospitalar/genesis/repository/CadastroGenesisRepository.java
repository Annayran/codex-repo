package br.com.zaya.mahospitalar.genesis.repository;

import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.zaya.mahospitalar.genesis.domain.CandidatoGenesis;
import br.com.zaya.mahospitalar.genesis.domain.ContextoBaseInstaladaGenesis;
import br.com.zaya.mahospitalar.genesis.domain.ReferenciaGenesisLocal;
import br.com.zaya.mahospitalar.genesis.service.PayloadGenesisFactory;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public final class CadastroGenesisRepository {
    private static final int ID_BASE = 1;

    public ContextoBaseInstaladaGenesis carregarParaEnvio(JdbcWrapper jdbc, CandidatoGenesis c)
            throws Exception {
        ReferenciaGenesisLocal cliente = unico(consultar(jdbc,
                "SELECT IDREGCLI,IDGENESIS,CODIGOEXTERNO FROM AD_GENCLIENTE " +
                        "WHERE IDBASE=? AND CODPARC=? AND STATUSCONC='CONCILIADO'",
                ps -> { ps.setInt(1, ID_BASE); ps.setBigDecimal(2, c.codParc); }),
                "cliente conciliado");
        ReferenciaGenesisLocal setor = carregarSetor(jdbc, c, cliente);
        if (setor.getIdRegistro() != null && cliente.getIdGenesis() != null) {
            final Long registroSetor = setor.getIdRegistro();
            setor = unico(consultar(jdbc,
                    "SELECT IDREGSET,IDGENESIS,CODIGOEXTERNO FROM AD_GENSECTOR " +
                            "WHERE IDBASE=? AND IDREGSET=? AND IDCLIENTEGEN=? AND STATUSCONC='CONCILIADO'",
                    ps -> { ps.setInt(1, ID_BASE); ps.setLong(2, registroSetor);
                        ps.setLong(3, cliente.getIdGenesis()); }), "setor conciliado do cliente");
        }
        ReferenciaGenesisLocal modelo = unico(consultar(jdbc,
                "SELECT IDREGMOD,IDGENESIS,CODIGOEXTERNO FROM AD_GENMODELO " +
                        "WHERE IDBASE=? AND CODPROD=? AND STATUSCONC='CONCILIADO'",
                ps -> { ps.setInt(1, ID_BASE); ps.setBigDecimal(2, c.codProd); }),
                "modelo conciliado");
        if (modelo.getIdGenesis() != null) {
            validarIdModeloDisponivel(jdbc, modelo.getIdGenesis(), c.codProd, null);
        }
        ReferenciaGenesisLocal item = unico(consultar(jdbc,
                "SELECT IDREGITEM,IDGENESIS,CODIGOEXTERNO FROM AD_GENITEMMAT " +
                        "WHERE IDBASE=? AND CODPROD=? AND UPPER(TRIM(NSERIE))=?",
                ps -> { ps.setInt(1, ID_BASE); ps.setBigDecimal(2, c.codProd);
                    ps.setString(3, PayloadGenesisFactory.normalizarSerie(c.serie)); }),
                "item-material local");
        return new ContextoBaseInstaladaGenesis(cliente, setor, ReferenciaGenesisLocal.ausente(),
                ReferenciaGenesisLocal.ausente(), modelo, item);
    }

    public ContextoBaseInstaladaGenesis carregar(JdbcWrapper jdbc, CandidatoGenesis candidato)
            throws Exception {
        ReferenciaGenesisLocal cliente = unico(consultar(jdbc,
                "SELECT IDREGCLI,IDGENESIS,CODIGOEXTERNO FROM AD_GENCLIENTE " +
                        "WHERE IDBASE=? AND (CODPARC=? OR (CODPARC IS NULL AND " +
                        "REGEXP_REPLACE(COALESCE(CNPJ,CPF),'[^0-9]','')=?))",
                ps -> { ps.setInt(1, ID_BASE); ps.setBigDecimal(2, candidato.codParc);
                    ps.setString(3, digitos(candidato.cnpjCpf)); }),
                "cliente local para CODPARC=" + candidato.codParc);
        ReferenciaGenesisLocal setor = carregarSetor(jdbc, candidato, cliente);
        ReferenciaGenesisLocal material = unico(consultar(jdbc,
                "SELECT IDREGMAT,IDGENESIS,CODIGOEXTERNO FROM AD_GENMATERIAL " +
                        "WHERE IDBASE=? AND CODGRUPOPROD=?",
                ps -> { ps.setInt(1, ID_BASE); ps.setBigDecimal(2, candidato.codGrupoProd); }),
                "material local para CODGRUPOPROD=" + candidato.codGrupoProd);
        ReferenciaGenesisLocal fabricante = unico(consultar(jdbc,
                "SELECT IDREGEMP,IDGENESIS,CODIGOEXTERNO FROM AD_GENEMPRESA " +
                        "WHERE IDBASE=? AND (UPPER(TRIM(CODIGOEXTERNO))=UPPER(TRIM(?)) OR " +
                        "UPPER(TRIM(MARCASANKHYA))=UPPER(TRIM(?)))",
                ps -> {
                    ps.setInt(1, ID_BASE); ps.setString(2, candidato.codigoExternoFabricante);
                    ps.setString(3, candidato.marca);
                }), "fabricante local para CODFAB=" + candidato.codFabricante +
                        " ou MARCA=" + candidato.marca);
        ReferenciaGenesisLocal modelo = unico(consultar(jdbc,
                "SELECT IDREGMOD,IDGENESIS,CODIGOEXTERNO FROM AD_GENMODELO " +
                        "WHERE IDBASE=? AND CODPROD=?",
                ps -> { ps.setInt(1, ID_BASE); ps.setBigDecimal(2, candidato.codProd); }),
                "modelo local para CODPROD=" + candidato.codProd);
        if (modelo.getIdRegistro() != null) {
            ReferenciaGenesisLocal materialModelo = unico(consultar(jdbc,
                    "SELECT E.IDREGMAT,E.IDGENESIS,E.CODIGOEXTERNO FROM AD_GENMATERIAL E " +
                            "JOIN AD_GENMODELO M ON M.IDBASE=E.IDBASE AND M.IDMATERIALGEN=E.IDGENESIS " +
                            "WHERE M.IDBASE=? AND M.IDREGMOD=?",
                    ps -> { ps.setInt(1, ID_BASE); ps.setLong(2, modelo.getIdRegistro()); }),
                    "material ja vinculado ao modelo");
            if (materialModelo.existe()) material = materialModelo.herdadaDoModelo();
        }
        if (modelo.getIdRegistro() != null && !fabricante.existe()) {
            fabricante = unico(consultar(jdbc,
                    "SELECT E.IDREGEMP,E.IDGENESIS,E.CODIGOEXTERNO FROM AD_GENEMPRESA E " +
                            "JOIN AD_GENMODELO M ON M.IDBASE=E.IDBASE AND M.IDEMPRESAGEN=E.IDGENESIS " +
                            "WHERE M.IDBASE=? AND M.IDREGMOD=?",
                    ps -> { ps.setInt(1, ID_BASE); ps.setLong(2, modelo.getIdRegistro()); }),
                    "fabricante ja vinculado ao modelo");
        }
        ReferenciaGenesisLocal item = unico(consultar(jdbc,
                "SELECT IDREGITEM,IDGENESIS,CODIGOEXTERNO FROM AD_GENITEMMAT " +
                        "WHERE IDBASE=? AND CODPROD=? AND UPPER(TRIM(NSERIE))=?",
                ps -> {
                    ps.setInt(1, ID_BASE);
                    ps.setBigDecimal(2, candidato.codProd);
                    ps.setString(3, PayloadGenesisFactory.normalizarSerie(candidato.serie));
                }), "item-material local para CODPROD=" + candidato.codProd +
                " e SERIE=" + PayloadGenesisFactory.normalizarSerie(candidato.serie));
        return new ContextoBaseInstaladaGenesis(cliente, setor, material, fabricante, modelo, item);
    }

    public void registrarCliente(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                 String codigoExterno) throws Exception {
        bloquearBase(jdbc);
        String documento = digitos(c.cnpjCpf);
        String cnpj = documento != null && documento.length() > 11 ? documento : null;
        String cpf = documento != null && documento.length() <= 11 ? documento : null;
        String sql = "UPDATE AD_GENCLIENTE SET IDGENESIS=?,CODIGOEXTERNO=?,RAZAOSOCIAL=?," +
                "NOMEFANTASIA=?,CNPJ=?,CPF=?,ENDERECO=?,ENDERECOBAIRRO=?,ENDERECOCIDADE=?," +
                "ENDERECOUF=?,CEP=?,TELEFONE=?,EMAIL=?,STATUSGENESIS=?,STATUSCONC='CONCILIADO'," +
                "MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE WHERE IDBASE=? AND CODPARC=?";
        int alterados;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            int i = 1;
            ps.setLong(i++, idGenesis); ps.setString(i++, limitar(codigoExterno, 200));
            ps.setString(i++, nome(c.razaoParceiro, c.nomeParceiro));
            ps.setString(i++, nome(c.nomeParceiro, c.razaoParceiro));
            ps.setString(i++, cnpj); ps.setString(i++, cpf);
            ps.setString(i++, limitar(c.endereco, 255)); ps.setString(i++, limitar(c.bairro, 120));
            ps.setString(i++, limitar(c.cidade, 120)); ps.setString(i++, limitar(c.uf, 2));
            ps.setString(i++, limitar(digitos(c.cep), 10)); ps.setString(i++, limitar(digitos(c.telefone), 30));
            ps.setString(i++, limitar(c.email, 255)); ps.setString(i++, status(c.parceiroAtivo));
            ps.setInt(i++, ID_BASE); ps.setBigDecimal(i, c.codParc);
            alterados = ps.executeUpdate();
        }
        validarQuantidade(alterados, "cliente", c.codParc);
        if (alterados == 0) inserirCliente(jdbc, c, idGenesis, codigoExterno, cnpj, cpf);
    }

    public void registrarSetor(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                               long idCliente, String codigoExterno,
                               ReferenciaGenesisLocal local) throws Exception {
        bloquearBase(jdbc);
        int alterados = 0;
        if (!local.existe()) {
            local = carregarSetor(jdbc, c,
                    new ReferenciaGenesisLocal(null, idCliente, null));
        }
        if (!local.existe()) {
            inserirSetor(jdbc, c, idGenesis, idCliente, codigoExterno);
            return;
        }
        String sql = "UPDATE AD_GENSECTOR SET IDGENESIS=?,IDCLIENTEGEN=?,CODIGOEXTERNO=?," +
                "NOME=?,STATUSGENESIS='Ativo',CODPARC=?,STATUSCONC='CONCILIADO'," +
                "MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE WHERE IDBASE=? AND IDREGSET=? " +
                "AND (CODPARC=? OR CODPARC IS NULL)";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idGenesis); ps.setLong(2, idCliente);
            ps.setString(3, limitar(codigoExterno, 200));
            ps.setString(4, nome(c.razaoParceiro, c.nomeParceiro)); ps.setBigDecimal(5, c.codParc);
            ps.setInt(6, ID_BASE);
            ps.setLong(7, local.getIdRegistro());
            ps.setBigDecimal(8, c.codParc);
            alterados = ps.executeUpdate();
        }
        validarQuantidade(alterados, "setor", c.codParc);
        if (alterados != 1) {
            throw new IllegalStateException("Setor local alterado ou vinculado a outro parceiro; " +
                    "revisar IDREGSET=" + local.getIdRegistro() + ".");
        }
    }

    public void registrarMaterial(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                  String codigoExterno) throws Exception {
        bloquearBase(jdbc);
        String sql = "UPDATE AD_GENMATERIAL SET IDGENESIS=?,CODIGOEXTERNO=?,NOME=?,GRAURISCO=?," +
                "STATUSGENESIS=?,STATUSCONC='CONCILIADO',MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE " +
                "WHERE IDBASE=? AND CODGRUPOPROD=?";
        int alterados;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idGenesis); ps.setString(2, limitar(codigoExterno, 200));
            ps.setString(3, nome(c.descricaoGrupo, "GRUPO " + c.codGrupoProd));
            ps.setString(4, limitar(c.grauRisco, 30)); ps.setString(5, status(c.produtoAtivo));
            ps.setInt(6, ID_BASE); ps.setBigDecimal(7, c.codGrupoProd);
            alterados = ps.executeUpdate();
        }
        validarQuantidade(alterados, "material", c.codGrupoProd);
        if (alterados == 0) inserirMaterial(jdbc, c, idGenesis, codigoExterno);
    }

    public void registrarFabricante(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                    String codigoExterno, ReferenciaGenesisLocal local) throws Exception {
        bloquearBase(jdbc);
        String filtro = local.existe() ? "IDREGEMP=?" :
                "(UPPER(TRIM(CODIGOEXTERNO))=UPPER(TRIM(?)) OR UPPER(TRIM(MARCASANKHYA))=UPPER(TRIM(?)))";
        String sql = "UPDATE AD_GENEMPRESA SET IDGENESIS=?,CODIGOEXTERNO=?,RAZAOSOCIAL=?," +
                "NOMEFANTASIA=?,MARCASANKHYA=COALESCE(MARCASANKHYA,?)," +
                "FORNECEDOR=1,FABRICANTE=1,STATUSGENESIS='Ativo'," +
                "STATUSCONC='CONCILIADO',MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE " +
                "WHERE IDBASE=? AND " + filtro;
        int alterados;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idGenesis); ps.setString(2, limitar(codigoExterno, 200));
            ps.setString(3, nome(c.nomeFabricante, c.marca)); ps.setString(4, nome(c.nomeFabricante, c.marca));
            ps.setString(5, limitar(c.marca, 100));
            ps.setInt(6, ID_BASE);
            if (local.existe()) {
                ps.setLong(7, local.getIdRegistro());
            } else {
                ps.setString(7, codigoExterno); ps.setString(8, c.marca);
            }
            alterados = ps.executeUpdate();
        }
        validarQuantidade(alterados, "fabricante", c.codigoFabricante);
        if (alterados == 0) inserirFabricante(jdbc, c, idGenesis, codigoExterno);
    }

    public void registrarModelo(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                long idFabricante, long idMaterial, String codigoExterno) throws Exception {
        bloquearBase(jdbc);
        validarIdModeloDisponivel(jdbc, idGenesis, c.codProd, null);
        String sql = "UPDATE AD_GENMODELO SET IDGENESIS=?,IDEMPRESAGEN=?,IDMATERIALGEN=?," +
                "CODIGOEXTERNO=?,NOME=?,STATUSGENESIS=?,NRANVISA=?,STATUSCONC='CONCILIADO'," +
                "MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE WHERE IDBASE=? AND CODPROD=?";
        int alterados;
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idGenesis); ps.setLong(2, idFabricante); ps.setLong(3, idMaterial);
            ps.setString(4, limitar(codigoExterno, 200)); ps.setString(5, nome(c.descricaoProduto, "PRODUTO " + c.codProd));
            ps.setString(6, status(c.produtoAtivo)); ps.setString(7, limitar(c.codAnvisa, 50));
            ps.setInt(8, ID_BASE); ps.setBigDecimal(9, c.codProd); alterados = ps.executeUpdate();
        }
        validarQuantidade(alterados, "modelo", c.codProd);
        if (alterados == 0) inserirModelo(jdbc, c, idGenesis, idFabricante, idMaterial, codigoExterno);
    }

    public void registrarIdModeloExistente(JdbcWrapper jdbc, ReferenciaGenesisLocal local,
                                            long idGenesis, String codigoExterno) throws Exception {
        if (!local.existe()) throw new IllegalStateException("Modelo local ausente para reconciliacao por ID.");
        bloquearBase(jdbc);
        validarIdModeloDisponivel(jdbc, idGenesis, null, local.getIdRegistro());
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "UPDATE AD_GENMODELO SET IDGENESIS=?,CODIGOEXTERNO=COALESCE(?,CODIGOEXTERNO)," +
                        "STATUSCONC='CONCILIADO',MENSAGEMCONC=NULL,DHCONCILIACAO=SYSDATE " +
                        "WHERE IDBASE=? AND IDREGMOD=?")) {
            ps.setLong(1, idGenesis); ps.setString(2, limitar(codigoExterno, 200));
            ps.setInt(3, ID_BASE); ps.setLong(4, local.getIdRegistro());
            if (ps.executeUpdate() != 1) {
                throw new IllegalStateException("Modelo local deixou de existir durante a reconciliacao.");
            }
        }
    }

    private void validarIdModeloDisponivel(JdbcWrapper jdbc, long idGenesis, BigDecimal codProd,
                                            Long idRegistroIgnorado) throws Exception {
        String sql = "SELECT IDREGMOD,CODPROD,CODIGOEXTERNO FROM AD_GENMODELO " +
                "WHERE IDBASE=? AND IDGENESIS=?" +
                (idRegistroIgnorado == null ? "" : " AND IDREGMOD<>?");
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ID_BASE);
            ps.setLong(2, idGenesis);
            if (idRegistroIgnorado != null) ps.setLong(3, idRegistroIgnorado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal produtoExistente = rs.getBigDecimal("CODPROD");
                    if (codProd != null && produtoExistente != null &&
                            codProd.compareTo(produtoExistente) == 0) continue;
                    throw new IllegalStateException("ID Genesis de modelo " + idGenesis +
                            " ja vinculado ao modelo local IDREGMOD=" + rs.getLong("IDREGMOD") +
                            ", CODPROD=" + (produtoExistente == null ? "<ausente>" : produtoExistente.toPlainString()) +
                            ", CODIGOEXTERNO=" + rs.getString("CODIGOEXTERNO") + ".");
                }
            }
        }
    }

    private ReferenciaGenesisLocal carregarSetor(JdbcWrapper jdbc, CandidatoGenesis c,
                                                  ReferenciaGenesisLocal cliente) throws Exception {
        List<SetorLocal> setores = new ArrayList<SetorLocal>();
        String sql = "SELECT IDREGSET,IDGENESIS,CODIGOEXTERNO,PADRAO FROM AD_GENSECTOR " +
                "WHERE IDBASE=? AND (CODPARC=? OR (CODPARC IS NULL AND ? IS NOT NULL AND IDCLIENTEGEN=?)) ORDER BY IDREGSET";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ID_BASE); ps.setBigDecimal(2, c.codParc);
            if (cliente.getIdGenesis() == null) {
                ps.setNull(3, java.sql.Types.NUMERIC); ps.setNull(4, java.sql.Types.NUMERIC);
            } else {
                ps.setLong(3, cliente.getIdGenesis()); ps.setLong(4, cliente.getIdGenesis());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) setores.add(new SetorLocal(referencia(rs), rs.getString("PADRAO")));
            }
        }
        if (setores.isEmpty()) return ReferenciaGenesisLocal.ausente();
        if (setores.size() == 1) return setores.get(0).referencia;
        ReferenciaGenesisLocal padrao = null;
        for (SetorLocal setor : setores) {
            if (!"S".equalsIgnoreCase(setor.padrao)) continue;
            if (padrao != null) throw new IllegalStateException("Mais de um setor local PADRAO='S' para CODPARC=" + c.codParc + ".");
            padrao = setor.referencia;
        }
        if (padrao != null) return padrao;
        throw new IllegalStateException("Mais de um setor local e nenhum PADRAO='S' para CODPARC=" + c.codParc + ".");
    }

    private void inserirCliente(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                String codigoExterno, String cnpj, String cpf) throws Exception {
        String sql = "INSERT INTO AD_GENCLIENTE (IDREGCLI,IDBASE,IDGENESIS,CODIGOEXTERNO," +
                "RAZAOSOCIAL,NOMEFANTASIA,CNPJ,CPF,ENDERECO,ENDERECOBAIRRO,ENDERECOCIDADE," +
                "ENDERECOUF,CEP,TELEFONE,EMAIL,STATUSGENESIS,CODPARC,STATUSCONC,MENSAGEMCONC," +
                "DHCONCILIACAO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'CONCILIADO',NULL,SYSDATE)";
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            int i=1; ps.setLong(i++, proximoId(jdbc,"AD_GENCLIENTE","IDREGCLI")); ps.setInt(i++,ID_BASE);
            ps.setLong(i++,idGenesis); ps.setString(i++,limitar(codigoExterno,200));
            ps.setString(i++,nome(c.razaoParceiro,c.nomeParceiro)); ps.setString(i++,nome(c.nomeParceiro,c.razaoParceiro));
            ps.setString(i++,cnpj); ps.setString(i++,cpf); ps.setString(i++,limitar(c.endereco,255));
            ps.setString(i++,limitar(c.bairro,120)); ps.setString(i++,limitar(c.cidade,120));
            ps.setString(i++,limitar(c.uf,2)); ps.setString(i++,limitar(digitos(c.cep),10));
            ps.setString(i++,limitar(digitos(c.telefone),30)); ps.setString(i++,limitar(c.email,255));
            ps.setString(i++,status(c.parceiroAtivo)); ps.setBigDecimal(i,c.codParc); ps.executeUpdate();
        }
    }

    private void inserirSetor(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                              long idCliente, String codigoExterno) throws Exception {
        String sql="INSERT INTO AD_GENSECTOR (IDREGSET,IDBASE,IDGENESIS,IDCLIENTEGEN,CODIGOEXTERNO,"+
                "NOME,STATUSGENESIS,CODPARC,PADRAO,STATUSCONC,MENSAGEMCONC,DHCONCILIACAO) "+
                "VALUES (?,?,?,?,?,?,'Ativo',?,'N','CONCILIADO',NULL,SYSDATE)";
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(sql)){
            ps.setLong(1,proximoId(jdbc,"AD_GENSECTOR","IDREGSET")); ps.setInt(2,ID_BASE);
            ps.setLong(3,idGenesis); ps.setLong(4,idCliente); ps.setString(5,limitar(codigoExterno,200));
            ps.setString(6,nome(c.razaoParceiro,c.nomeParceiro)); ps.setBigDecimal(7,c.codParc); ps.executeUpdate();
        }
    }

    private void inserirMaterial(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                 String codigoExterno) throws Exception {
        String sql="INSERT INTO AD_GENMATERIAL (IDREGMAT,IDBASE,IDGENESIS,CODIGOEXTERNO,NOME,"+
                "GRAURISCO,STATUSGENESIS,CODGRUPOPROD,STATUSCONC,MENSAGEMCONC,DHCONCILIACAO) "+
                "VALUES (?,?,?,?,?,?,?,?,'CONCILIADO',NULL,SYSDATE)";
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(sql)){
            ps.setLong(1,proximoId(jdbc,"AD_GENMATERIAL","IDREGMAT")); ps.setInt(2,ID_BASE);
            ps.setLong(3,idGenesis); ps.setString(4,limitar(codigoExterno,200));
            ps.setString(5,nome(c.descricaoGrupo,"GRUPO "+c.codGrupoProd)); ps.setString(6,limitar(c.grauRisco,30));
            ps.setString(7,status(c.produtoAtivo)); ps.setBigDecimal(8,c.codGrupoProd); ps.executeUpdate();
        }
    }

    private void inserirFabricante(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                                   String codigoExterno) throws Exception {
        String sql="INSERT INTO AD_GENEMPRESA (IDREGEMP,IDBASE,IDGENESIS,CODIGOEXTERNO,RAZAOSOCIAL,"+
                "NOMEFANTASIA,FORNECEDOR,FABRICANTE,STATUSGENESIS,MARCASANKHYA,STATUSCONC,"+
                "MENSAGEMCONC,DHCONCILIACAO) VALUES (?,?,?,?,?,?,1,1,'Ativo',?,'CONCILIADO',NULL,SYSDATE)";
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(sql)){
            ps.setLong(1,proximoId(jdbc,"AD_GENEMPRESA","IDREGEMP")); ps.setInt(2,ID_BASE);
            ps.setLong(3,idGenesis); ps.setString(4,limitar(codigoExterno,200));
            ps.setString(5,nome(c.nomeFabricante,c.marca)); ps.setString(6,nome(c.nomeFabricante,c.marca));
            ps.setString(7,limitar(c.marca,100)); ps.executeUpdate();
        }
    }

    private void inserirModelo(JdbcWrapper jdbc, CandidatoGenesis c, long idGenesis,
                               long idFabricante, long idMaterial, String codigoExterno) throws Exception {
        String sql="INSERT INTO AD_GENMODELO (IDREGMOD,IDBASE,IDGENESIS,IDEMPRESAGEN,IDMATERIALGEN,"+
                "CODIGOEXTERNO,NOME,STATUSGENESIS,NRANVISA,CODPROD,STATUSCONC,MENSAGEMCONC,"+
                "DHCONCILIACAO) VALUES (?,?,?,?,?,?,?,?,?,?,'CONCILIADO',NULL,SYSDATE)";
        try(PreparedStatement ps=jdbc.getConnection().prepareStatement(sql)){
            ps.setLong(1,proximoId(jdbc,"AD_GENMODELO","IDREGMOD")); ps.setInt(2,ID_BASE);
            ps.setLong(3,idGenesis); ps.setLong(4,idFabricante); ps.setLong(5,idMaterial);
            ps.setString(6,limitar(codigoExterno,200)); ps.setString(7,nome(c.descricaoProduto,"PRODUTO "+c.codProd));
            ps.setString(8,status(c.produtoAtivo)); ps.setString(9,limitar(c.codAnvisa,50));
            ps.setBigDecimal(10,c.codProd); ps.executeUpdate();
        }
    }

    private List<ReferenciaGenesisLocal> consultar(JdbcWrapper jdbc, String sql, Parametros parametros)
            throws Exception {
        List<ReferenciaGenesisLocal> itens = new ArrayList<ReferenciaGenesisLocal>();
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(sql)) {
            parametros.aplicar(ps);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) itens.add(referencia(rs)); }
        }
        return itens;
    }

    private static ReferenciaGenesisLocal referencia(ResultSet rs) throws Exception {
        long idRegistro = rs.getLong(1);
        long idGenesis = rs.getLong(2);
        Long id = rs.wasNull() ? null : idGenesis;
        return new ReferenciaGenesisLocal(idRegistro, id, rs.getString(3));
    }

    private static ReferenciaGenesisLocal unico(List<ReferenciaGenesisLocal> itens, String descricao) {
        if (itens.size() > 1) {
            StringBuilder candidatos = new StringBuilder();
            for (int i=0; i<Math.min(itens.size(),10); i++) {
                ReferenciaGenesisLocal item = itens.get(i);
                if (i>0) candidatos.append("; ");
                candidatos.append("registro=").append(item.getIdRegistro())
                        .append(", IDGenesis=").append(item.getIdGenesis());
            }
            throw new IllegalStateException("Mais de uma correspondencia para " + descricao +
                    ". Total=" + itens.size() + "; candidatos (ate 10): " + candidatos +
                    ". Nenhum vinculo escolhido. Revisar identidade e dependencias antes de consolidar.");
        }
        return itens.isEmpty() ? ReferenciaGenesisLocal.ausente() : itens.get(0);
    }

    private void bloquearBase(JdbcWrapper jdbc) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT IDBASE FROM AD_GENBASE WHERE IDBASE=? FOR UPDATE")) {
            ps.setInt(1, ID_BASE);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new IllegalStateException("Base Instalada Genesis IDBASE=1 inexistente.");
            }
        }
    }

    private long proximoId(JdbcWrapper jdbc, String tabela, String coluna) throws Exception {
        try (PreparedStatement ps = jdbc.getConnection().prepareStatement(
                "SELECT NVL(MAX(" + coluna + "),0)+1 FROM " + tabela);
             ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getLong(1); }
    }

    private static void validarQuantidade(int quantidade, String entidade, Object chave) {
        if (quantidade > 1) throw new IllegalStateException("Mais de um registro local de " + entidade +
                " atualizado para a chave " + chave + ".");
    }

    private static String status(String value) {
        return "N".equalsIgnoreCase(value) || "INATIVO".equalsIgnoreCase(value) ? "Inativo" : "Ativo";
    }

    private static String nome(String primeiro, String segundo) {
        if (primeiro != null && !primeiro.trim().isEmpty()) return limitar(primeiro.trim(), 255);
        if (segundo != null && !segundo.trim().isEmpty()) return limitar(segundo.trim(), 255);
        throw new IllegalStateException("Nome obrigatorio ausente para cadastro Genesis.");
    }

    private static String digitos(String value) { return value == null ? null : value.replaceAll("\\D", ""); }
    private static String limitar(String value, int max) {
        if (value == null) return null;
        return value.length() <= max ? value : value.substring(0, max);
    }

    private interface Parametros { void aplicar(PreparedStatement ps) throws Exception; }
    private static final class SetorLocal {
        final ReferenciaGenesisLocal referencia; final String padrao;
        SetorLocal(ReferenciaGenesisLocal referencia, String padrao) {
            this.referencia = referencia; this.padrao = padrao;
        }
    }
}
