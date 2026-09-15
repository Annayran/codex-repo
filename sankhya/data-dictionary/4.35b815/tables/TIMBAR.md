# TIMBAR — TIMBAR

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimBalaoReneg | Balões | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BARCONTRATO | Contrato | I |  |  | N |
| BARDTVENC | Dt. Vencimento | D |  |  | N |
| BARRENEG | Renegociação | I |  |  | N |
| BARSEQ | Sequência | I |  |  | N |
| BARVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimBalaoReneg / [TIMBAR](TIMBAR.md) | TimRenegociacao / [TIMLVR](TIMLVR.md) | BARCONTRATO->LVRCONTRATO; BARRENEG->LVRCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
