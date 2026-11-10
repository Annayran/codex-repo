/*
 * Decompiled with CFR 0.152.
 */
package org.ImportadorPedido.models;

import java.math.BigDecimal;

public class ResultadoReconciliacaoProduto {
    private final Status status;
    private final BigDecimal codOriginal;
    private final BigDecimal codResolvido;
    private final String referenciaInformada;
    private final String motivo;
    private final String causaProvavel;

    private ResultadoReconciliacaoProduto(Status status, BigDecimal codOriginal, BigDecimal codResolvido, String referenciaInformada, String motivo, String causaProvavel) {
        this.status = status;
        this.codOriginal = codOriginal;
        this.codResolvido = codResolvido;
        this.referenciaInformada = referenciaInformada;
        this.motivo = motivo;
        this.causaProvavel = causaProvavel;
    }

    public static ResultadoReconciliacaoProduto ok(BigDecimal codOriginal, String referenciaInformada) {
        return ResultadoReconciliacaoProduto.ok(codOriginal, referenciaInformada, "COD_VALIDO", "DADO_CONSISTENTE");
    }

    public static ResultadoReconciliacaoProduto ok(BigDecimal codOriginal, String referenciaInformada, String motivo, String causaProvavel) {
        return new ResultadoReconciliacaoProduto(Status.OK, codOriginal, codOriginal, referenciaInformada, motivo, causaProvavel);
    }

    public static ResultadoReconciliacaoProduto corrigido(BigDecimal codOriginal, BigDecimal codResolvido, String referenciaInformada, String motivo, String causaProvavel) {
        return new ResultadoReconciliacaoProduto(Status.COD_CORRIGIDO_POR_REFERENCIA, codOriginal, codResolvido, referenciaInformada, motivo, causaProvavel);
    }

    public static ResultadoReconciliacaoProduto ignorar(BigDecimal codOriginal, String referenciaInformada, String motivo, String causaProvavel) {
        return new ResultadoReconciliacaoProduto(Status.IGNORAR_ITEM, codOriginal, null, referenciaInformada, motivo, causaProvavel);
    }

    public boolean deveIgnorar() {
        return Status.IGNORAR_ITEM.equals((Object)this.status);
    }

    public boolean foiCorrigido() {
        return Status.COD_CORRIGIDO_POR_REFERENCIA.equals((Object)this.status);
    }

    public Status getStatus() {
        return this.status;
    }

    public BigDecimal getCodOriginal() {
        return this.codOriginal;
    }

    public BigDecimal getCodResolvido() {
        return this.codResolvido;
    }

    public String getReferenciaInformada() {
        return this.referenciaInformada;
    }

    public String getMotivo() {
        return this.motivo;
    }

    public String getCausaProvavel() {
        return this.causaProvavel;
    }

    public static enum Status {
        OK,
        COD_CORRIGIDO_POR_REFERENCIA,
        IGNORAR_ITEM;

    }
}

