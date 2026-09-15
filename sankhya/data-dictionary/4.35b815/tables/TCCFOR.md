# TCCFOR — Formula Calculo Civil

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaCivil | Formula Calculo Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORM | Cod. Formula | I |  |  | N |
| DESCRFORM | Descrição Formula | S |  |  | N |
| FORMULA | Formula | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | FormulaCivil / [TCCFOR](TCCFOR.md) | CODFORM->CODFORM | I |
| HistoricoFormula / [TCCHTF](TCCHTF.md) | FormulaCivil / [TCCFOR](TCCFOR.md) | CODFORM->CODFORM | I |
| NegociacaoCivil / [TCCNEG](TCCNEG.md) | FormulaCivil / [TCCFOR](TCCFOR.md) | CODFORM->CODFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
