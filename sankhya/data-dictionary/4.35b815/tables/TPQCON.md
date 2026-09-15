# TPQCON — TPQCON

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Consenso | Consenso | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | CODUSU | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| NUAVALIACAO | NUAVALIACAO | I |  |  | N |
| NUPESQ | NUPESQ | I |  |  | N |
| NUPESQAVALIADO | NUPESQAVALIADO | I |  |  | N |
| NUPESQCONSENSO | NUPESQCONSENSO | I |  |  | N |
| PLANODEACAO | PLANODEACAO | S |  |  | N |
| STATUS | STATUS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Consenso / [TPQCON](TPQCON.md) | Pesquisa / [TPQPES](TPQPES.md) | NUPESQCONSENSO->NUPESQ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
