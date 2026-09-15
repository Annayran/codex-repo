# TGFEST — Estoque

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Estoque | Estoque | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODAGREGACAO | Cód. de Agregação | S |  |  | N |
| CODBARRA | Cód. de Barras | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODPROD | Cód. Prod. | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DTFABRICACAO | Data de Fabricação | D |  |  | N |
| DTVAL | Data de Validade | D |  |  | N |
| ESTDOCAWMS | Estoque em docas de entrada do WMS | I |  |  | S |
| ESTMAX | Estoque Máximo | F |  |  | N |
| ESTMIN | Estoque Mínimo | F |  |  | N |
| ESTOQUE | Estoque | F |  |  | N |
| PERCGERMIN | % Germinação | F |  |  | N |
| PERCPUREZA | % Pureza | F |  |  | N |
| RESERVADO | Reservado | F |  |  | N |
| STATUSLOTE | Status Lote | S |  |  | N |
| TIPO | Tipo | S |  |  | N |
| WMSBLOQUEADO | Bloqueado no WMS | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Estoque / [TGFEST](TGFEST.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Estoque / [TGFEST](TGFEST.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| Estoque / [TGFEST](TGFEST.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| Estoque / [TGFEST](TGFEST.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Estoque / [TGFEST](TGFEST.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | Estoque / [TGFEST](TGFEST.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
