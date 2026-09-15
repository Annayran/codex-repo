# TGFDRC — TGFDRC

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RebaixaDeCusto | Rebaixa De Custo | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODLOCALORIG | Local | I |  |  | N |
| CODPROD | Cod. Produto | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| CUSMEGERAN | Custo Médio Gerencial (Antes) | F |  |  | N |
| CUSMEGERAP | Custo Médio Gerencial (Após) | F |  |  | N |
| DTREF | Data Referência | D |  |  | N |
| NUDESTINACAO | NUDESTINACAO | I |  |  | N |
| NUNOTA | Nro. Único | I |  |  | N |
| QTDESTAP | Vlr. Estoque (Após) | F |  |  | N |
| QTDESTAT | Vlr. Estoque (Antes) | F |  |  | N |
| QTDESTOQ | Qtd. Estoque | F |  |  | N |
| STATUS | STATUS | S |  |  | N |
| VLRDESTINAR | Valor a Destinar | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCALORIG->CODLOCAL | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| RebaixaDeCusto / [TGFDRC](TGFDRC.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
