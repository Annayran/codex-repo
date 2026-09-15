# TGFDNP — Descrição de Natureza por Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescricaoNaturezaParceiro | Descrição da Natureza por Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODNAT | Código da Natureza | I |  |  | N |
| CODPARC | Código do Parceiro | I |  |  | N |
| DESCRNAT | Descrição da Natureza | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescricaoNaturezaParceiro / [TGFDNP](TGFDNP.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| DescricaoNaturezaParceiro / [TGFDNP](TGFDNP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Natureza / [TGFNAT](TGFNAT.md) | DescricaoNaturezaParceiro / [TGFDNP](TGFDNP.md) | CODNAT->CODNAT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
