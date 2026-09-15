# TGFAVE — Averbações do Seguro MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AverbacaoSeguroMDFE | Averbações do Seguro MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUMAPOLICE | Nro. Apólice | S |  |  | N |
| NUMAVERB | Nro. Averbação | S |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SeguroMDFE / [TGFMDFESEG](TGFMDFESEG.md) | AverbacaoSeguroMDFE / [TGFAVE](TGFAVE.md) | NUMAPOLICE->NUMAPOLICE; NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
