# TGFPRJ — Projeção de receitas e despesas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProjecaoReceitaDespesa | Projeção de receitas e despesas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| PROJDESP | Projeção despesa | F |  |  | N |
| PROJREC | Projeção receita | F |  |  | N |
| REALDESP | Realizado despesa | F |  |  | N |
| REALREC | Realizado receita | F |  |  | N |
| REFERENCIA | Referência | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ImportacaoXMLNotas / [TGFIXN](TGFIXN.md) | ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | CODPROJ->CODPROJ | I |
| ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ProjecaoReceitaDespesa / [TGFPRJ](TGFPRJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
