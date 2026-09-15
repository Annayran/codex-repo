# TIMINR — TIMINR

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIntermedReneg | Intermediárias | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| INRCONTRATO | Contrato | I |  |  | N |
| INRDTVENC | Dt. Vencimento | D |  |  | N |
| INRRENEG | Renegociação | I |  |  | N |
| INRSEQ | Sequência | I |  |  | N |
| INRTIPOINTERMED | Tipo de Intermediária | I |  |  | N |
| INRVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIntermedReneg / [TIMINR](TIMINR.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | INRCONTRATO->LVRCODIGO; INRRENEG->LVRCONTRATO | I |
| TimIntermedReneg / [TIMINR](TIMINR.md) | TimTipoIntermed / [TIMTIN](TIMTIN.md) | INRTIPOINTERMED->TINCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
