# TGFMDFPRAPGFRE — MDF-e Prazo do Pagamento de Frete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ManifestoPrazoPgtoFrete | MDF-e Prazo do Pagamento de Frete | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUVIAG | Nro. Viagem | I |  |  | N |
| PARCELA | Parcela | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| SEQPGFRE | Sequência do pagamento de frete | I |  |  | N |
| SEQPRAPGFRE | Sequência do prazo de pagamento de frete | I |  |  | N |
| VALOR | Valor | F |  |  | N |
| VENCIMENTO | Vencimento | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |
| ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |
| ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
