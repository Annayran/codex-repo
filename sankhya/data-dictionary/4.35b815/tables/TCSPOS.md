# TCSPOS — Pesquisa X Ordem de Serviço

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PesquisaOrdemServico | Pesquisa X Ordem de Serviço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APLICAVEL | APLICAVEL | S |  |  | N |
| CODFLD | CODFLD | I |  |  | N |
| NUMITEM | NUMITEM | I |  |  | N |
| NUMOS | NUMOS | I |  |  | N |
| NUPESQ | NUPESQ | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Pesquisa / [TPQPES](TPQPES.md) | PesquisaOrdemServico / [TCSPOS](TCSPOS.md) | NUPESQ->NUPESQ | I |
| PesquisaOrdemServico / [TCSPOS](TCSPOS.md) | Pesquisa / [TPQPES](TPQPES.md) | NUPESQ->NUPESQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
