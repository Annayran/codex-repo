# TGFFCV — Fórmulas Comissão Premiação

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
| FormulaComissaoPremiacao | Fórmulas de Comissão para Premiações | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORM | Fórmula | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Cód. Vendedor | I |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormulaComissaoPremiacao / [TGFFCV](TGFFCV.md) | FormulasComissao / [TGFFOC](TGFFOC.md) | CODFORM->CODFORM | I |
| Vendedor / [TGFVEN](TGFVEN.md) | FormulaComissaoPremiacao / [TGFFCV](TGFFCV.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
