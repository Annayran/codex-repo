# TIMINT — TIMINT

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIntermediaria | Intermediárias | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| INTCONTRATO | Contrato | I |  |  | N |
| INTDTVENC | Dt. Vencimento | D |  |  | N |
| INTSEQ | Sequência | I |  |  | N |
| INTTIPOINTERMED | Tipo de Intermediária | I |  |  | N |
| INTVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIntermediaria / [TIMINT](TIMINT.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | INTCONTRATO->LTVCODIGO | I |
| TimIntermediaria / [TIMINT](TIMINT.md) | TimTipoIntermed / [TIMTIN](TIMTIN.md) | INTTIPOINTERMED->TINCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
