# TGFDNE — Definição de Nó em Estrutura

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DefinicaoNoEstrutura | Definição de Nó em Estrutura | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MULTIPOCOR | MULTIPOCOR | S |  |  | N |
| NIVELFINAL | Nível Final | I |  |  | N |
| NIVELINICIAL | Nível Inicial | I |  |  | N |
| NUEST | Número da Estrutura | I |  |  | N |
| NUINSTANCIA | Número da Instância | I |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DefinicaoNoEstrutura / [TGFDNE](TGFDNE.md) | EstruturaOrganograma / [TGFCES](TGFCES.md) | NUEST->NUEST | I |
| EstruturaOrganograma / [TGFCES](TGFCES.md) | DefinicaoNoEstrutura / [TGFDNE](TGFDNE.md) | NUEST->NUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
