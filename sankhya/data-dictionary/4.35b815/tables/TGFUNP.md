# TGFUNP — Unidade Fiscal por Parceiro

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
| UnidadeFiscalParceiro | Unidade Fiscal por Parceiro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVOL | Cód. Volume | S |  |  | N |
| CODVOLPARC | Cód. Volume Parceiro | S |  |  | N |
| DHALTER | Data da Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| UnidadeFiscalParceiro / [TGFUNP](TGFUNP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Volume / [TGFVOL](TGFVOL.md) | UnidadeFiscalParceiro / [TGFUNP](TGFUNP.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
