# TGFMDFPGFRE — MDF-e Pagamento de Frete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ManifestoPgtoFrete | MDF-e Pagamento de Frete | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENCIA | Agência | S |  |  | N |
| CHAVEPIX | Chave PIX | S |  |  | N |
| CODBANCO | Número do banco | S |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPARCINTPGFRE | Parceiro de Pag. do Frete | I |  |  | N |
| ENVIO | Tipo de envio | S |  |  | N |
| INDPAG | Forma de pagamento | S |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |
| SEQPGFRE | Sequência do pagamento de frete | I |  |  | N |
| VLRCONTRATO | Valor do Contrato | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Manifesto / [TGFMDFE](TGFMDFE.md) | ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | Manifesto / [TGFMDFE](TGFMDFE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | ManifestoCompPgtoFrete / [TGFMDFCOMPGFRE](TGFMDFCOMPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | ParceiroPgtoFrete / [TGFPAR](TGFPAR.md) | CODPARCINTPGFRE->CODPARC | I |
| ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| ManifestoPrazoPgtoFrete / [TGFMDFPRAPGFRE](TGFMDFPRAPGFRE.md) | ManifestoPgtoFrete / [TGFMDFPGFRE](TGFMDFPGFRE.md) | NUVIAG->NUVIAG; SEQMDFE->SEQMDFE; SEQPGFRE->SEQPGFRE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
