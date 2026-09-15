# TRPCAM — Campo Replay

- Módulo técnico: [TRP](../modules/TRP.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CampoReplay | Campo Replay | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPODESTINO | Campo de Destino | S |  |  | N |
| CAMPOORIGEM | Campo de Origem | S |  |  | N |
| FILTRO | Filtro | S |  |  | N |
| NULIGACAO | Num. Ligação | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoReplay / [TRPCAM](TRPCAM.md) | TabelaReplay / [TRPTAB](TRPTAB.md) | NULIGACAO->NULIGACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
