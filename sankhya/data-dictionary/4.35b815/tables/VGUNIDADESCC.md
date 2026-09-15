# VGUNIDADESCC — View Unidades Civil

- Módulo técnico: [VGU](../modules/VGU.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstoqueUnidadesCivil | Estoque Unidades Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | CODEMP | I |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| DESCRPROD | DESCRPROD | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EstoqueUnidadesCivil / [VGUNIDADESCC](VGUNIDADESCC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
