# TGFSTMVPRODET — Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BaseStMediaVendaProDet | BaseStMediaVendaProDet | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| NUNOTA | Nro. Doc | I |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| REFERENCIA | Referência | D |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VLRDESC | Desconto | F |  |  | N |
| VLRLIQ | Vlr. Total Líq. | F |  |  | N |
| VLRTOT | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BaseStMediaVendaProDet / [TGFSTMVPRODET](TGFSTMVPRODET.md) | BaseStMediaVendaPro / [TGFSTMVPRO](TGFSTMVPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
