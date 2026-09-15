# TGFCMC7 — Prefixo do cheque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrefixoCheque | Prefixo do cheque | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBCO | Código do banco | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| PREFIXO | Prefixo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Banco / [TSIBCO](TSIBCO.md) | PrefixoCheque / [TGFCMC7](TGFCMC7.md) | CODBCO->CODBCO | L |
| PrefixoCheque / [TGFCMC7](TGFCMC7.md) | Banco / [TSIBCO](TSIBCO.md) | CODBCO->CODBCO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
