# TGFGCL — ATRIBUI VARIACAO COLUNA/LINHA

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| VariacaoDimensaoGrade | Variação Dimensão Grade de Produtos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ABVVAR | Abreviação | S |  |  | N |
| ATUNUVERSAO | Atualizar versão | S |  |  | N |
| IDATB | ID Dimensão | I |  |  | N |
| IDGRADE | ID Grade | I |  |  | N |
| IDVCL | Nro. único | I |  |  | N |
| NOMVAR | Variação | S |  |  | N |
| NUVERSAO | Versão | I |  |  | N |
| ORDCAMP | Ordem | I |  |  | N |
| PRMCPR | Permite Compra | S |  |  | N |
| PRMVND | Permite Venda | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DimensaoGrade / [TGFATB](TGFATB.md) | VariacaoDimensaoGrade / [TGFGCL](TGFGCL.md) | IDATB->IDATB; IDGRADE->IDGRADE | I |
| VariacaoDimensaoGrade / [TGFGCL](TGFGCL.md) | DimensaoGrade / [TGFATB](TGFATB.md) | IDATB->IDATB; IDGRADE->IDGRADE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
