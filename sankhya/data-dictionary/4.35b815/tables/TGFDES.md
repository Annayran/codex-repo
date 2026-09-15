# TGFDES — Descontos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 30
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Desconto | Descontos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| APLICDESCPORLOCAL | Aplicar desconto por local | S |  |  | N |
| ATUNUVERSAO | ATUNUVERSAO | S |  |  | N |
| AVISARVCT | Avisar Vencimento | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODTAB | Atua sobre a tabela de preço | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DESCRPROMOCAO | Descrição | S |  |  | N |
| DTFINAL | Data Final | D |  |  | N |
| DTINICIAL | Data Inicial | D |  |  | N |
| GRUPODESCPARC | Grupo Desconto Parceiro | S |  |  | N |
| GRUPODESCPROD | Grupo Desconto Produto/Serviço | S |  |  | N |
| GRUPODESCVEND | Grupo Desconto Vendedor | S |  |  | N |
| LIQUIDACAO | Liquidação | S |  |  | N |
| NUPROMOCAO | Nro. único | I |  |  | N |
| NUVERSAO | NUVERSAO | I |  |  | N |
| PERCDESCBONIF | % Desc. Bonificação | F |  |  | N |
| PERCENTUAL | Percentual | F |  |  | N |
| TIPGRUPO | Tipo Grupo | S |  |  | S |
| TIPGRUPOPROD | Tipo Desconto Produto | S |  |  | S |
| TIPPROMOCAO | Tipo Promoção | S |  |  | N |
| USADESCCTRL | Usa desconto por controle | S |  |  | N |
| USADESCESP | Usa desconto especial | S |  |  | N |
| USADESCQTD | Usa desconto por quantidade | S |  |  | N |
| VLRDESC | Valor do desconto | F |  |  | N |
| VLRVENDA | Valor de venda | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Desconto / [TGFDES](TGFDES.md) | DescontoPorQuantidade / [TGFDPQ](TGFDPQ.md) | NUPROMOCAO->NUPROMOCAO | I |
| Desconto / [TGFDES](TGFDES.md) | DescontosEspeciais / [TGFDEP](TGFDEP.md) | NUPROMOCAO->NUPROMOCAO | I |
| Desconto / [TGFDES](TGFDES.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Desconto / [TGFDES](TGFDES.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Desconto / [TGFDES](TGFDES.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Desconto / [TGFDES](TGFDES.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Desconto / [TGFDES](TGFDES.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Desconto / [TGFDES](TGFDES.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Desconto / [TGFDES](TGFDES.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
