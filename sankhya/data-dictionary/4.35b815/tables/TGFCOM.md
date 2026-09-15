# TGFCOM — Valores de Comissões de Vendedores

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 23
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ValorComissaoVendedor | Valores de Comissões de Vendedores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODEVENTO | Evento | I |  |  | N |
| CODFORM | Cód. Fórmula | I |  |  | N |
| CODFUNC | Funcionário | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODVEND | Cód. Vendedor | I |  |  | N |
| DHALTER | Data do fechamento | H |  |  | N |
| INDICEPRODUTIVIDADE | Índice de Produtividade | F |  |  | N |
| NUFECHAMENTO | Nro do fechamento da comissão | I |  |  | N |
| NUFIN | Número Financeiro | I |  |  | N |
| NUFINORIG | Financeiro de origem | I |  |  | N |
| NUMITEMORIG | Número Item Origem | I |  |  | N |
| NUMOSORIG | Número OS Origem | I |  |  | N |
| NUNOTAORIG | Nota de origem | I |  |  | N |
| QTDHORA | Quantidade de Horas | I |  |  | N |
| QTDHORAEXTRA | Quantidade de Horas extras | I |  |  | N |
| REFERENCIA | Referência | H |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRCOM | Valor da comissão | F |  |  | N |
| VLRHORA | Valor Hora | F |  |  | N |
| VLRHORAEXTRA | Valor hora extra | F |  |  | N |
| VLRRESIDUOCOM | Resíduo de Comissão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | ValorComissaoVendedor / [TGFCOM](TGFCOM.md) | NUNOTA->NUNOTAORIG | I |
| ValorComissaoVendedor / [TGFCOM](TGFCOM.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| ViewCabecalhoNota / [VGFCAB2](VGFCAB2.md) | ValorComissaoVendedor / [TGFCOM](TGFCOM.md) | NUNOTA->NUNOTAORIG | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
