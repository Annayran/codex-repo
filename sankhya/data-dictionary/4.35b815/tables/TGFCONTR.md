# TGFCONTR — Contratante MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ContratanteMDFE | Contratante MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCCONTR | Cód. Parceiro Contratante | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratanteMDFE / [TGFCONTR](TGFCONTR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCCONTR->CODPARC | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | ContratanteMDFE / [TGFCONTR](TGFCONTR.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
