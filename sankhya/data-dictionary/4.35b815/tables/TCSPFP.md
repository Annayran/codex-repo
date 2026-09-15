# TCSPFP — Pendencia Faturamento Prorata

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PendenciaFaturamentoProRata | Pendência Faturamento ProRata | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODOCOR | Ocorrência | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| DTOCOR | Referência | D |  |  | N |
| NUMCONTRATO | Contrato | I |  |  | N |
| PENDENCIA | Pendência | S |  |  | N |
| VALOR | Valor | F |  |  | N |
| VLRACERTADO | Vlr. Acertado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PendenciaFaturamentoProRata / [TCSPFP](TCSPFP.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
