# TGFVCE — Variação de Custo e Estoque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VariacaoCustoEstoque | Variação de Custo e Estoque | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHMOV | Data e hora do movimento | H |  |  | N |
| QTDENTRADA | Entrada | F |  |  | N |
| QTDEST | Estoque | F |  |  | N |
| QTDSAIDA | Saída | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| VariacaoCustoEstoque / [TGFVCE](TGFVCE.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| VariacaoCustoEstoque / [TGFVCE](TGFVCE.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| VariacaoCustoEstoque / [TGFVCE](TGFVCE.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
