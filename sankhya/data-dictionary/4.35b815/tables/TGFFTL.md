# TGFFTL — Tamanho dos lotes nas fórmulas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Tamanholoteformula | Tamanho dos lotes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| TAMANHO | Tamanho | F |  |  | N |
| VARIACAO | Variação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | Tamanholoteformula / [TGFFTL](TGFFTL.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| Tamanholoteformula / [TGFFTL](TGFFTL.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
