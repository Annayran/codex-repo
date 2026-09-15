# TGFIPA — Informações dos Períodos Anteriores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InformacoesPeriodosAnteriores | Informações dos Períodos Anteriores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROD | Produto | I |  |  | N |
| DIASPERIODO | Dias Úteis | I |  |  | N |
| DTFINAL | Data Final | D |  |  | N |
| DTINCIAL | Data Inicial | D |  |  | N |
| NUPCA | Núm. Planejamento | I |  |  | N |
| QTDVENDIDO | Qtd. Vendida | F |  |  | N |
| SEQPERIODO | Período | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItensPlanejamentoCompra / [TGFIPC](TGFIPC.md) | InformacoesPeriodosAnteriores / [TGFIPA](TGFIPA.md) | CODPROD->CODPROD; NUPCA->NUPCA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
