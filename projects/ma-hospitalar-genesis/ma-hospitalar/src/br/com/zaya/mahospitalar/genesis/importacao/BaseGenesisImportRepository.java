package br.com.zaya.mahospitalar.genesis.importacao;

import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.sankhya.jape.wrapper.JapeWrapper;
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public final class BaseGenesisImportRepository {
    public boolean salvar(BigDecimal idBase, RegistroBaseGenesis registro) throws Exception {
        JapeWrapper dao = JapeFactory.dao(registro.getAba().getEntidade());
        DynamicVO existente = localizar(dao, idBase, registro);
        if (existente == null) {
            FluidCreateVO novo = dao.create().set("IDBASE", idBase).set("STATUSCONC", "PENDENTE");
            if (registro.getAba() == AbaBaseGenesis.SETOR) {
                novo.set("PADRAO", "N");
            }
            preencher(novo, registro.getCampos());
            novo.save();
            return true;
        }

        FluidUpdateVO atualizacao = dao.prepareToUpdate(existente);
        preencher(atualizacao, registro.getCampos());
        atualizacao.update();
        return false;
    }

    public void iniciar(BigDecimal idBase, String arquivo, BigDecimal usuario) throws Exception {
        JapeWrapper dao = JapeFactory.dao("GENBASE");
        DynamicVO base = dao.findByPK(idBase);
        if (base == null) {
            throw new IllegalArgumentException("Base Genesis " + idBase + " nao localizada.");
        }
        FluidUpdateVO update = dao.prepareToUpdate(base)
                .set("STATUS", "PREPARACAO")
                .set("ORIGEM", "PLANILHA_GENESIS")
                .set("DHCARGA", new Timestamp(System.currentTimeMillis()));
        if (arquivo != null && !arquivo.trim().isEmpty()) update.set("ARQUIVOORIGEM", arquivo.trim());
        if (usuario != null) update.set("CODUSUCARGA", usuario);
        update.update();
    }

    public void finalizar(BigDecimal idBase) throws Exception {
        JapeWrapper dao = JapeFactory.dao("GENBASE");
        DynamicVO base = dao.findByPK(idBase);
        if (base == null) throw new IllegalArgumentException("Base Genesis nao localizada.");
        dao.prepareToUpdate(base)
                .set("STATUS", "IMPORTADA")
                .set("DHCARGA", new Timestamp(System.currentTimeMillis()))
                .update();
    }

    private DynamicVO localizar(JapeWrapper dao, BigDecimal idBase, RegistroBaseGenesis registro) throws Exception {
        if (registro.getAba() == AbaBaseGenesis.PROPRIEDADE || registro.getAba() == AbaBaseGenesis.SITUACAO) {
            return dao.findOne("IDBASE = ? AND TIPODOMINIO = ? AND IDGENESIS = ?",
                    idBase, registro.getAba().getTipoDominio(), registro.getIdGenesis());
        }
        return dao.findOne("IDBASE = ? AND IDGENESIS = ?", idBase, registro.getIdGenesis());
    }

    private void preencher(FluidCreateVO destino, Map<String, Object> campos) {
        for (Map.Entry<String, Object> campo : campos.entrySet()) destino.set(campo.getKey(), campo.getValue());
    }

    private void preencher(FluidUpdateVO destino, Map<String, Object> campos) {
        for (Map.Entry<String, Object> campo : campos.entrySet()) destino.set(campo.getKey(), campo.getValue());
    }
}
