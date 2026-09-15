# TGFPPT — Tabela Precificação por TOP

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrecificacaoPorTop | Precificação por TOP | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODTAB | Tabela de Preço | I |  |  | N |
| CODTIPOPER | Tipo de Operação | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
