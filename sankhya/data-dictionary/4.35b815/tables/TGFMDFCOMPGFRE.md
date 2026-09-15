# TGFMDFCOMPGFRE — MDF-e Componente do Pagamento de Frete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ManifestoCompPgtoFrete | MDF-e Componenetes do Pagamento de Frete | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| INDALTODESEMP | Ind. de op. de transp. de alto desempenho | S |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQCOMPGFRE | Sequência do componente de pagamento de frete | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| SEQPGFRE | Sequência do pagamento de frete | I |  |  | N |
| TPCOMP | Tipo | S |  |  | N |
| VLRADIANT | Valor do Adiantamento | F |  |  | N |
| VLRCOMP | Valor do componente | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |
| ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
