package br.com.zaya.teleworld.flow.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import br.com.sankhya.jape.vo.DynamicVO;
import br.com.sankhya.jape.wrapper.JapeFactory;
import br.com.zaya.teleworld.flow.model.service.RevisaoProdutoService;
import br.com.zaya.teleworld.flow.model.service.RotinaLog;
import br.com.zaya.teleworld.flow.sankhya.services.FlowSinalizador;

/** Consultas Jape na sessao gerenciada pelo evento/regra, sem commit manual. */
public class RevisaoProdutoController {
    private final RevisaoProdutoService service =
            new RevisaoProdutoService(new FlowSinalizador());

    public void incluir(DynamicVO item) throws Exception {
        BigDecimal nuNota = item.asBigDecimal("NUNOTA");
        if (nuNota == null) {
            throw new Exception("Item sem NUNOTA: nao foi possivel identificar o movimento para sinalizar a inclusao.");
        }
        DynamicVO nota = JapeFactory.dao("CabecalhoNota").findByPK(nuNota);
        if (nota == null) {
            throw new Exception("Cabecalho nao encontrado para verificar o movimento da inclusao.");
        }
        String tipoMovimento = nota.asString("TIPMOV");
        if (!"C".equals(tipoMovimento) && !"V".equals(tipoMovimento)) {
            RotinaLog.detalhe("sinal de inclusao ignorado: TIPMOV diferente de C ou V");
            return;
        }
        RotinaLog.info("inclusao nota=" + item.asBigDecimal("NUNOTA") + " produto=" + item.asBigDecimal("CODPROD"));
        DynamicVO produto = buscarProduto(item.asBigDecimal("CODPROD"));
        service.sinalizarInclusao(produto.getProperty("AD_IDINSTPRN"));
    }

    public void validarNota(BigDecimal nuNota) throws Exception {
        RotinaLog.info("validacao de revisao iniciada nota=" + nuNota);
        if (nuNota == null) {
            throw new Exception("Nao foi possivel identificar a nota para validar a revisao dos produtos.");
        }
        Collection<DynamicVO> itens = JapeFactory.dao("ItemNota").find("NUNOTA = ?", nuNota);
        // A ausencia de itens nao constitui pendencia de revisao.
        if (itens.isEmpty()) {
            RotinaLog.info("validacao sem bloqueio nota=" + nuNota + " motivo=sem_itens");
            return;
        }
        Set<BigDecimal> verificados = new HashSet<BigDecimal>();
        for (DynamicVO item : itens) {
            BigDecimal codProd = item.asBigDecimal("CODPROD");
            if (codProd == null || verificados.add(codProd.stripTrailingZeros())) {
                DynamicVO produto = buscarProduto(codProd);
                service.validarConfirmacao(nuNota, codProd,
                        produto.getProperty("AD_IDINSTPRN"), produto.asString("AD_REVISADO"));
            }
        }
        RotinaLog.info("validacao sem bloqueio nota=" + nuNota + " produtos_verificados=" + verificados.size());
    }

    private DynamicVO buscarProduto(BigDecimal codProd) throws Exception {
        if (codProd == null) {
            throw new Exception("Item sem CODPROD: nao foi possivel verificar a revisao.");
        }
        DynamicVO produto = JapeFactory.dao("Produto").findByPK(codProd);
        if (produto == null) {
            throw new Exception("Produto " + codProd + " nao encontrado para verificar a revisao.");
        }
        return produto;
    }
}
