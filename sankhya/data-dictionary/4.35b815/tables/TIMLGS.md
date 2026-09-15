# TIMLGS — TABLE TIMLGS

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacaoGarantiaSeguro | Garantia - Seguro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LGSAPOLICE | Apólice | S |  |  | N |
| LGSCODIGO | Código | I |  |  | N |
| LGSCOMISSAO | Comissão % | F |  |  | N |
| LGSCONTRATO | Cod. Contrato | I |  |  | N |
| LGSCORRETORA | Corretora | I |  |  | N |
| LGSDTCANCELAMENTO | Dt. Cancelamento | D |  |  | N |
| LGSDTFINVIGENCIA | Dt. Término Vigência | D |  |  | N |
| LGSDTINIVIGENCIA | Dt. Início Vigência | D |  |  | N |
| LGSSEGURADORA | Seguradora | I |  |  | N |
| LGSVLRCOBERTURA | Vlr. Cobertura | F |  |  | N |
| LGSVLRPREMIO | Vlr. Prêmio | F |  |  | N |
| LGSVLRSEGURO | Vlr. Seguro | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | LOCCODIGO->LGSCONTRATO | I |
| TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | Parceiro / [TGFPAR](TGFPAR.md) | LGSSEGURADORA->CODPARC | I |
| TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | LGSCORRETORA->CODPARC | I |
| TimLocacaoGarantiaSeguro / [TIMLGS](TIMLGS.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LGSCONTRATO->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
