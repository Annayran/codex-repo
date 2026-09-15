# TCSCXT — Centro de resultado por tipo de negociação

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CentroResultadoPorNegociacao | Centro Resultado Por Negociação | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODTPN | Cód. Tipo de Negociação Pré-Venda | I |  |  | N |
| NURFE | Modelo de Proposta | I |  |  | N |
| NURFEFR | Modelo Folha de Rosto | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | Relatorio / [TSIRFE](TSIRFE.md) | NURFE->NURFE | I |
| CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | RelatorioDependente / [TSIRFE](TSIRFE.md) | NURFEFR->NURFE | I |
| TipoNegociacaoPreVenda / [TCSTPN](TCSTPN.md) | CentroResultadoPorNegociacao / [TCSCXT](TCSCXT.md) | CODTPN->CODTPN | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
