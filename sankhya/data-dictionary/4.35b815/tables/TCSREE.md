# TCSREE — Requisito da Etapa

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RequisitoEtapa | Requisito da Etapa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODREQ | Requisito | I |  |  | N |
| NUFAP | FAP | I |  |  | N |
| NUMETAPA | Etapa | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RequisitoEtapa / [TCSREE](TCSREE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| RequisitoFap / [TCSREQ](TCSREQ.md) | RequisitoEtapa / [TCSREE](TCSREE.md) | CODREQ->CODREQ; NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
