# TGFPERI — Produtos Perigosos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoPerigosoNotaMDFE | Produtos Perigosos Notas MDF-e | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUMONU | Nro. ONU | S |  |  | N |
| NUNOTA | Nro. Único da Nota | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |
| QTDTOTPROD | Qtd. Total por Produto | S |  |  | N |
| SEQMDFE | Sequência do manifesto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NotasMDF / [TGFNMDFE](TGFNMDFE.md) | ProdutoPerigosoNotaMDFE / [TGFPERI](TGFPERI.md) | NUNOTA->NUNOTA; NUVIAG->NUVIAG; SEQMDFE->SEQMDFE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
