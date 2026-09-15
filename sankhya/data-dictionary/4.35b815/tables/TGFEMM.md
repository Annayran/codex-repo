# TGFEMM — Estoque Mínimo e Máximo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstoqueMinMax | Estoque Mínimo e Máximo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODBARRA | Código de Barra | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DTVAL | Data de Validade | H |  |  | N |
| ESTMAX | Estoque máximo | F |  |  | N |
| ESTMIN | Estoque mínimo | F |  |  | N |
| ESTOQUE | Estoque | F |  |  | N |
| RESERVADO | Reservado | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EstoqueMinMax / [TGFEMM](TGFEMM.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
