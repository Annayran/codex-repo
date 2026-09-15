# TGFFIC — TGFFIC

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaIcms | Fórmula Icms | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORM | Código | I |  |  | N |
| DESCRFORM | Descrição da Fórmula | S |  |  | N |
| FORMULA | Fórmula | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaICMS / [TGFICM](TGFICM.md) | FormulaIcms / [TGFFIC](TGFFIC.md) | CODFORMBASICM->CODFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
