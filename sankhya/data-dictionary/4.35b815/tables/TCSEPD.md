# TCSEPD — Etapa predecessora

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaPredecessora | Etapa Predecessora | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUFAP | NUFAP | I |  |  | N |
| NUMETAPA | NUMETAPA | I |  |  | N |
| NUMETAPAPRED | NUMETAPAPRED | I |  |  | N |
| TIPO | TIPO | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaPredecessora / [TCSEPD](TCSEPD.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPAPRED->NUMETAPA | I |
| EtapaProjeto / [TCSFET](TCSFET.md) | EtapaPredecessora / [TCSEPD](TCSEPD.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
