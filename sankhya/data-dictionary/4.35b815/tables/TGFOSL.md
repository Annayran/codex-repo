# TGFOSL — Ordem de Sugestão Locais

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
| OrdemSugestaoLocal | Ordem de Sugestão de Locais com Estoque | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODLOCAL | Cod.Loc | I |  |  | N |
| CODTIPOPER | Cód.Tipo Operação | I |  |  | N |
| ORDEM | Ordem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemSugestaoLocal / [TGFOSL](TGFOSL.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| TipoOperacao / [TGFTOP](TGFTOP.md) | OrdemSugestaoLocal / [TGFOSL](TGFOSL.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
