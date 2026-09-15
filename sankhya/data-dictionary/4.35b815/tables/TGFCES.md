# TGFCES — Estrutura

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstruturaOrganograma | Estrutura do Organograma | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| DESCRROOT | Descrição Root | S |  |  | N |
| NUEST | Número da Estrutura | I |  |  | N |
| TIPVISUAL | TIPVISUAL | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DefinicaoNoEstrutura / [TGFDNE](TGFDNE.md) | EstruturaOrganograma / [TGFCES](TGFCES.md) | NUEST->NUEST | I |
| EstruturaOrganograma / [TGFCES](TGFCES.md) | DefinicaoNoEstrutura / [TGFDNE](TGFDNE.md) | NUEST->NUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
