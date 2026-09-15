# TGFMCX — Movimentações de Caixa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MovimentacaoCaixa | Movimentações de Caixa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data | H |  |  | N |
| NROUNICO | Número Único | I |  |  | N |
| NUBCO | Número do Banco | I |  |  | N |
| NUCAIXA | Número do caixa | I |  |  | N |
| NUMNSU | NSU | S |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| RECDESP | Receita Despesa | I |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ControleCaixa / [TGFCAI](TGFCAI.md) | MovimentacaoCaixa / [TGFMCX](TGFMCX.md) | NUCAIXA->NUCAIXA | I |
| MovimentacaoCaixa / [TGFMCX](TGFMCX.md) | ControleCaixa / [TGFCAI](TGFCAI.md) | NUCAIXA->NUCAIXA | I |
| MovimentacaoCaixa / [TGFMCX](TGFMCX.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
