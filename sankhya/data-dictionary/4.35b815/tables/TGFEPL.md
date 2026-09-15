# TGFEPL — Etapa planejamento produção

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaPlanejamentoProducao | Etapa do planejamento de produção | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABRVETAPA | Abrev. etapa | S |  |  | N |
| CQL | CQL | S |  |  | N |
| DTFIM | Data de finalização | D |  |  | N |
| DTINI | Data de início | D |  |  | N |
| ETAPA | Etapa | I |  |  | N |
| GERAPROD | Gera produção | S |  |  | N |
| LEADTIME | Lead time | I |  |  | N |
| LEADTIMEFIM | Lead time finalização | I |  |  | N |
| LEADTIMETOT | Lead time total | I |  |  | N |
| NUMPLAN | NUMPLAN | I |  |  | N |
| NUNOTA | Núm. produção | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| TOPPROD | Top de produção | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanejamentoProducaoQuibasa / [TGFPLP](TGFPLP.md) | EtapaPlanejamentoProducao / [TGFEPL](TGFEPL.md) | NUMPLAN->NUMPLAN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
