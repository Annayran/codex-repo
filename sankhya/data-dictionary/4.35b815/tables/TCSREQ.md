# TCSREQ — Requisitos FAP

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RequisitoFap | Requisitos da FAP | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CASOUSO | Caso de uso | S |  |  | N |
| CODREQ | Requisição | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NUFAP | FAP | I |  |  | N |
| PESO | Peso | F |  |  | N |
| TODAETAPA | Participa em toda etapa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProjetoServico / [TCSFAP](TCSFAP.md) | RequisitoFap / [TCSREQ](TCSREQ.md) | NUFAP->NUFAP | I |
| RequisitoFap / [TCSREQ](TCSREQ.md) | ProjetoServico / [TCSFAP](TCSFAP.md) | NUFAP->NUFAP | I |
| RequisitoFap / [TCSREQ](TCSREQ.md) | RequisitoEtapa / [TCSREE](TCSREE.md) | CODREQ->CODREQ; NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
