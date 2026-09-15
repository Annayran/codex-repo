# TGFNCM — CODNCM

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Ncm | NCM | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CATEGORIA | Categoria | S |  |  | N |
| CODNCM | NCM | S |  |  | N |
| DFIMVIGENCIA | Data Fim Vigência | D |  |  | N |
| DINIVIGENCIA | Data Início Vigência | D |  |  | N |
| DSNCM | Descrição | S |  |  | N |
| DSUNITRIBUTACAO | Descrição Unidade de Tributação | S |  |  | N |
| IPI | IPI | S |  |  | N |
| UNITRIBUTACAO | Unidade de Tributação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Produto / [TGFPRO](TGFPRO.md) | Ncm / [TGFNCM](TGFNCM.md) | NCM->CODNCM | I |
| VinculoLocacaoBemMovel / [TCOMVLB](TCOMVLB.md) | Ncm / [TGFNCM](TGFNCM.md) | NCM->CODNCM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
