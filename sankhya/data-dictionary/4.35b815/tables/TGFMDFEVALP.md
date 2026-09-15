# TGFMDFEVALP — Vale Pedágio MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ValePedagioMDFE | Vale Pedágio MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CATEGCOMBVEIC | Categoria de combinação veicular | S |  |  | N |
| CODPARCFORN | Cód. Parceiro Fornecedor | I |  |  | N |
| CODPARCPAG | Cód. Parceiro Pagante | I |  |  | N |
| NUCOMPRA | Identificador do vale pedagio obrigatório – IDVPO | S |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TPVALEPED | Tipo do vale pedágio | S |  |  | N |
| VLRPEDAGIO | Vlr. Pedágio | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | ValePedagioMDFE / [TGFMDFEVALP](TGFMDFEVALP.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ValePedagioMDFE / [TGFMDFEVALP](TGFMDFEVALP.md) | ParceiroFornecedor / [TGFPAR](TGFPAR.md) | CODPARCFORN->CODPARC | I |
| ValePedagioMDFE / [TGFMDFEVALP](TGFMDFEVALP.md) | ParceiroPagante / [TGFPAR](TGFPAR.md) | CODPARCPAG->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
