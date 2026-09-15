# TSICVIEWS — TSICVIEWS

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewCustomizada | View Customizada | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário de alteração | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dh. Alteração | H |  |  | N |
| DHSINC | Dh.Sinc DB | H |  |  | N |
| NOME | Nome | S |  |  | N |
| NUVIEW | Nu.View | I |  |  | N |
| SELECTVIEW | Select | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewCustomizada / [TSICVIEWS](TSICVIEWS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
