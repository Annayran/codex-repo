# TGFGVC — CriterioPreAcordoVerba

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CriterioPreAcordoVerba | Criterios de Meta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| NUCRITERIO | Nro Critério | I |  |  | N |
| NUPREACORDO | Nro Pré-Acordo | I |  |  | N |
| TIPOCRITERIO | Tipo de Critério | S |  |  | N |
| TIPOMETA | Tipo de Meta | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoPreAcordoVerba / [TGFGVE](TGFGVE.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |
| ApuracaoProPreAcordoVerba / [TGFGVA](TGFGVA.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |
| CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | FaixasPreAcordoVerba / [TGFGVF](TGFGVF.md) | NUCRITERIO->NUCRITERIO | I |
| CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | PreAcordoVerba / [TGFGVP](TGFGVP.md) | NUPREACORDO->NUPREACORDO | I |
| CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | NUCRITERIO->NUCRITERIO | I |
| FaixasPreAcordoVerba / [TGFGVF](TGFGVF.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |
| PreAcordoVerba / [TGFGVP](TGFGVP.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUPREACORDO->NUPREACORDO | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | CriterioPreAcordoVerba / [TGFGVC](TGFGVC.md) | NUCRITERIO->NUCRITERIO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
