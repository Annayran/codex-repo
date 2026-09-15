# TCCHTF — Troca Formula CC

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoFormula | Troca Formula CC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | Contrato | I |  |  | N |
| CODFORM | Fórmula | I |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| DTINICIO | Data | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoFormula / [TCCHTF](TCCHTF.md) | ContratoCivil / [TCCCON](TCCCON.md) | CODCC->CODCC | I |
| HistoricoFormula / [TCCHTF](TCCHTF.md) | FormulaCivil / [TCCFOR](TCCFOR.md) | CODFORM->CODFORM | I |
| HistoricoFormula / [TCCHTF](TCCHTF.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
