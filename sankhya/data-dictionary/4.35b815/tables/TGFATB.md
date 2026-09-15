# TGFATB — TABELA DE ATRIBUTOS (COLUNA E LINHA)

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DimensaoGrade | Dimensão Grade de Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABVATB | Abreviação | S |  |  | N |
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| IDATB | Nro. único | I |  |  | N |
| IDGRADE | Nro. único grade | I |  |  | N |
| NOMEATB | Dimensão | S |  |  | N |
| NUVERSAO | Versão | I |  |  | N |
| ORDCAMP | Ordem | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | DimensaoGrade / [TGFATB](TGFATB.md) | IDGRADE->IDGRADE | I |
| DimensaoGrade / [TGFATB](TGFATB.md) | ConfiguracaoGradeProduto / [TGFGRD](TGFGRD.md) | IDGRADE->IDGRADE | I |
| DimensaoGrade / [TGFATB](TGFATB.md) | VariacaoDimensaoGrade / [TGFGCL](TGFGCL.md) | IDATB->IDATB; IDGRADE->IDGRADE | I |
| VariacaoDimensaoGrade / [TGFGCL](TGFGCL.md) | DimensaoGrade / [TGFATB](TGFATB.md) | IDATB->IDATB; IDGRADE->IDGRADE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
