# TCSAGF — Agenda Fixa

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AgendaFixa | Agenda Fixa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| DIASEM | Dia da Senama | I |  |  | N |
| HORARIO | Horário Marcado | H |  |  | N |
| OBSERVACOES | Observações | S |  |  | N |
| TIPOPER | Tipo da Operação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendaFixa / [TCSAGF](TCSAGF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AgendaFixa / [TCSAGF](TCSAGF.md) | Servico / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AgendaFixa / [TCSAGF](TCSAGF.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
