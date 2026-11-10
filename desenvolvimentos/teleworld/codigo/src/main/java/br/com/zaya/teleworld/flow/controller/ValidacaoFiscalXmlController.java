package br.com.zaya.teleworld.flow.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.zaya.teleworld.flow.model.service.RotinaLog;
import br.com.zaya.teleworld.flow.model.service.ValidacaoFiscalXmlService;
import br.com.zaya.teleworld.flow.model.service.ValidacaoFiscalXmlService.ItemXml;

/** Leitura na transacao da confirmacao, sem alterar dados ou controlar commit. */
public final class ValidacaoFiscalXmlController {
    private final ValidacaoFiscalXmlService service = new ValidacaoFiscalXmlService();

    public void validarNota(BigDecimal nuNota) throws Exception {
        if (nuNota == null) throw new RotinaLog.MensagemCliente("Nota não identificada para comparar o XML.");
        Collection<DynamicVO> arquivos = JapeFactory.dao("ImportacaoXMLNotas").find("NUNOTA = ?", nuNota);
        // Notas sem importacao ficam fora da comparacao; a revisao anterior continua ativa.
        if (arquivos.isEmpty()) return;
        if (arquivos.size() != 1) {
            throw new RotinaLog.MensagemCliente("Nota " + nuNota
                    + " possui mais de um XML na TGFIXN. Confira o vínculo da importação.");
        }
        Map<BigDecimal, ItemXml> xml = service.ler(arquivos.iterator().next().asClob("XML"));
        Collection<DynamicVO> itens = JapeFactory.dao("ItemNota").find("NUNOTA = ? AND SEQUENCIA > 0", nuNota);
        Set<BigDecimal> encontrados = new HashSet<BigDecimal>();
        for (DynamicVO item : itens) {
            BigDecimal sequencia = item.asBigDecimal("SEQUENCIAFISCAL");
            ItemXml fiscal = sequencia == null ? null : xml.get(sequencia.stripTrailingZeros());
            if (fiscal == null) {
                throw new RotinaLog.MensagemCliente("Nota " + nuNota + ", item " + item.asBigDecimal("SEQUENCIA")
                        + ": não foi possível vincular SEQUENCIAFISCAL ao nItem do XML. Confira a importação.");
            }
            BigDecimal codProd = item.asBigDecimal("CODPROD");
            DynamicVO produto = codProd == null ? null : JapeFactory.dao("Produto").findByPK(codProd);
            if (produto == null) throw new RotinaLog.MensagemCliente("Produto não encontrado na TGFPRO para a nota " + nuNota + ".");
            BigDecimal aliquota = null;
            BigDecimal codIpi = produto.asBigDecimal("CODIPI");
            if (fiscal.temIpi() && codIpi != null) {
                DynamicVO ipi = JapeFactory.dao("AliquotaIPI").findByPK(codIpi);
                if (ipi != null) aliquota = ipi.asBigDecimal("PERCENTUAL");
            }
            service.validar(fiscal, produto.asString("NCM"), produto.asString("TEMIPICOMPRA"),
                    aliquota, nuNota, codProd, sequencia);
            encontrados.add(sequencia.stripTrailingZeros());
        }
        if (!encontrados.equals(xml.keySet())) {
            throw new RotinaLog.MensagemCliente("Nota " + nuNota
                    + ": existem itens do XML sem correspondência na nota. Confira a importação.");
        }
    }
}
