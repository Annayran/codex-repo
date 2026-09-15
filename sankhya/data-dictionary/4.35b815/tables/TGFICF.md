# TGFICF — Ítens de Coeficientes Financiamentos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemCoeficienteFinanciamento | Tabela para os itens de coeficientes para cálculo dos financiamentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARENCIA | Carência | I |  |  | N |
| CODTCF | Tabela Controle Financeiras | I |  |  | N |
| COEFICIENTE | Coeficiente | F |  |  | N |
| QTDPARCELAS | Quantidade Parcelas | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemCoeficienteFinanciamento / [TGFICF](TGFICF.md) | ControleFinanceira / [TGFTCF](TGFTCF.md) | CODTCF->CODTCF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
