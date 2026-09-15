# TIMLTC — TABLE TIMLTC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacaoGarantiaTituloCap | Garantia - Título de Capitalização | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LTCAGENCIA | Agência | S |  |  | N |
| LTCAPOLICE | Apólice | S |  |  | N |
| LTCBANCO | Banco | I |  |  | N |
| LTCCODIGO | Código | I |  |  | N |
| LTCCOMISSAO | % Comissão | F |  |  | N |
| LTCCONTRATO | Contrato | I |  |  | N |
| LTCCORRETORA | Corretora | I |  |  | N |
| LTCDTINIVIGENCIA | Início Vigência | D |  |  | N |
| LTCDTRESGATE | Dt. Resgate | D |  |  | N |
| LTCNRODOC | Nro. Documento | S |  |  | N |
| LTCSEGURADORA | Seguradora | I |  |  | N |
| LTCVLRPREMIO | Vlr. Prêmio | F |  |  | N |
| LTCVLRTITULO | Vlr. Título | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | LOCCODIGO->LTCCONTRATO | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | Banco / [TSIBCO](TSIBCO.md) | LTCBANCO->CODBCO | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | Parceiro / [TGFPAR](TGFPAR.md) | LTCSEGURADORA->CODPARC | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LTCCORRETORA->CODPARC | I |
| TimLocacaoGarantiaTituloCap / [TIMLTC](TIMLTC.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LTCCONTRATO->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
