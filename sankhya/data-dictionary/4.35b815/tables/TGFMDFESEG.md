# TGFMDFESEG — Seguro MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| SeguroMDFE | Seguro MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCRESPSEG | Responsável do seguro | I |  |  | N |
| CODPARCSEG | Seguradora | I |  |  | N |
| NUMAPOLICE | Nro. Apólice | S |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | AverbacaoSeguroMDFE / [TGFAVE](TGFAVE.md) | NUMAPOLICE->NUMAPOLICE; NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCSEG->CODPARC | I |
| SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | Parceiro2 / [TGFPAR](TGFPAR.md) | CODPARCRESPSEG->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
