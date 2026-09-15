# TGFINSSREF — INSS Referência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| InssReferencia | INSS Referência | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BASE | Base INSS | F |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| COMPETENCIA | Competência | D |  |  | N |
| VLRIMP | Vlr. Imposto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| InssReferencia / [TGFINSSREF](TGFINSSREF.md) | CabecalhoTabelaIrpfInss / [TGFPFX](TGFPFX.md) | COMPETENCIA->COMPETENCIA | I |
| InssReferencia / [TGFINSSREF](TGFINSSREF.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
