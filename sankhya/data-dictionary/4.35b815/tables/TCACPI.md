# TCACPI — Ítens de Precificação

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItensPrecificacao | Ítens de Precificação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPARC | Cód.Parceiro | I |  |  | N |
| NUCURSO | Cód.Curso | I |  |  | N |
| OBS | Observação | S |  |  | N |
| PRECOTOTAL | Preço Total | F |  |  | N |
| PRECOUNIT | Preço Unitário | F |  |  | N |
| QTD | Quantidade | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPrecificacao / [TCACPI](TCACPI.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ItensPrecificacao / [TCACPI](TCACPI.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ItensPrecificacao / [TCACPI](TCACPI.md) | PrecificacaoCurso / [TCACPR](TCACPR.md) | NUCURSO->NUCURSO | I |
| PrecificacaoCurso / [TCACPR](TCACPR.md) | ItensPrecificacao / [TCACPI](TCACPI.md) | NUCURSO->NUCURSO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
