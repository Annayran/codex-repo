# TGFSFP — Simulações de Formas de Pagamento

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SimulacaoFormaPagamento | Simulação de Forma de Pagamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPVENDA | Cód. Tipo Negociação | I |  |  | N |
| DTVENC | Dt. Vencimento | D |  |  | N |
| NUNOTA | Nro. Único Nota | I |  |  | N |
| PARCELA | Parcela | I |  |  | N |
| TAXAJURO | Taxa de Juro | F |  |  | N |
| VALOR | Valor | F |  |  | N |
| VALORLIQUIDO | Valor Líquido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | SimulacaoFormaPagamento / [TGFSFP](TGFSFP.md) | NUNOTA->NUNOTA | I |
| SimulacaoFormaPagamento / [TGFSFP](TGFSFP.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
