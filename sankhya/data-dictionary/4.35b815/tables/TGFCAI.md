# TGFCAI — Controle de Caixas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ControleCaixa | Controle de Caixas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta | I |  |  | N |
| CODPDV | Código do PDV | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONFERIDO | Conferido | S |  |  | N |
| DESPESA | Despesa | F |  |  | N |
| DTABERTURA | Dt. Abertura | H |  |  | N |
| DTFECHAMENTO | Dt. Fechamento | H |  |  | N |
| NUCAIXA | Núm. Caixa | I |  |  | N |
| RECEITA | Receita | F |  |  | N |
| SALDOINICIAL | Saldo Inicial | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | ControleCaixa / [TGFCAI](TGFCAI.md) | NUCAIXA->NUCAIXA | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | MovimentacaoCaixa / [TGFMCX](TGFMCX.md) | NUCAIXA->NUCAIXA | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | OrdemCarga / [TGFORD](TGFORD.md) | NUCAIXA->NUCAIXA | I |
| ControleCaixa / [TGFCAI](TGFCAI.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| MovimentacaoCaixa / [TGFMCX](TGFMCX.md) | ControleCaixa / [TGFCAI](TGFCAI.md) | NUCAIXA->NUCAIXA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
