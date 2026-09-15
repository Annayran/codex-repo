# TGFBAR — Códigos de Barras

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CodigoBarras | Código de Barras | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBARRA | Cód. Barras | S |  |  | N |
| CODPROD | Cód. Produto | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CODVOL | Unidade de Volume | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CodigoBarras / [TGFBAR](TGFBAR.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | CodigoBarras / [TGFBAR](TGFBAR.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
