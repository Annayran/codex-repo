# TCIIBE — Bens da Nota Fiscal

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BemNotafiscal | Bens da Nota Fiscal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód. bem | S |  |  | N |
| CODPROD | Cód. produto | I |  |  | N |
| NUNOTA | Nro. nota | I |  |  | N |
| ORDEM | Ordem de entrada do bem | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BemNotafiscal / [TCIIBE](TCIIBE.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| BemNotafiscal / [TCIIBE](TCIIBE.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| BemNotafiscal / [TCIIBE](TCIIBE.md) | Local / [TCILOC](TCILOC.md) | CODBEM->CODBEM; CODPROD->CODPROD; NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| Local / [TCILOC](TCILOC.md) | BemNotafiscal / [TCIIBE](TCIIBE.md) | CODBEM->CODBEM; CODPROD->CODPROD; NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
