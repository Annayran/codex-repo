# TGFUTV — UnidadesTransporteVazia

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadesTransporteVazia | UnidadesTransporteVazia | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUNITV | CODUNITV | I |  |  | N |
| IDUNIDTRANSVAZIA | ID Unidade Transporte Vazia | S |  |  | N |
| NUVIAG | NUVIAG | I |  |  | N |
| SEQMDFE | SEQMDFE | I |  |  | N |
| TIPOUNITRANSVAZIA | Tipo Unidade Transporte Vazia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadesTransporteVazia / [TGFUTV](TGFUTV.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
