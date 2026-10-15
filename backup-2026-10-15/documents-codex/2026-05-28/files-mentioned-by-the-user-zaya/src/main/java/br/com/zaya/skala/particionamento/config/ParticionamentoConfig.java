package br.com.zaya.skala.particionamento.config;

public final class ParticionamentoConfig {
    private ParticionamentoConfig() {
    }

    public static final double CAPACIDADE_PADRAO = 28.0;

    public static final String CAMPO_PERMITE_PART = "AD_PERMITEPART";
    public static final String CAMPO_PARTICIONADO = "AD_PARTICIONADO";
    public static final String CAMPO_QTD_PALETE_ITEM_OE = "AD_QTDPALETES";
    public static final String CAMPO_QTD_CAIXA_ITEM_OE = "AD_QTDCAIXA";

    public static final String VALOR_SIM = "S";
    public static final String VALOR_NAO = "N";

    public static final String JAPE_PROPERTY_DESFAZENDO_PARTICIONAMENTO = "ZAYA_SKALA_DESFAZENDO_PARTICIONAMENTO";
}
