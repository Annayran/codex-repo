# TGFNPC — Natureza Projeto Conta Contábil

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NaturezaProjetoCtaContabil | Natureza Projeto Conta Contábil | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTACTB | Conta reduzida | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPROJ | Cód. Projeto | I |  |  | N |
| PRIORIZARRATEIO | Considerar rateio como prioridade? | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Natureza / [TGFNAT](TGFNAT.md) | NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | CODNAT->CODNAT | I |
| NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | PlanoConta /  | CODCTACTB->CODCTACTB | I |
| NaturezaProjetoCtaContabil / [TGFNPC](TGFNPC.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
