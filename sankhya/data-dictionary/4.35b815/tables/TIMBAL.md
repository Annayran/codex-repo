# TIMBAL — TIMBAL

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimBalao | Balões | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BALCONTRATO | Contrato | I |  |  | N |
| BALDTVENC | Dt. Vencimento | D |  |  | N |
| BALSEQ | Sequência | I |  |  | N |
| BALVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimBalao / [TIMBAL](TIMBAL.md) | TimContratoVendaLote / [TIMLTV](TIMLTV.md) | BALCONTRATO->LTVCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
