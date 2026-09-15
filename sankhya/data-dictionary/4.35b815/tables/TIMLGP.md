# TIMLGP — TABLE TIMLGP

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimLocacaoGarantiaCaucao | Garantia - Caução/Poupança | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| LGPAGENCIA | Agência | S |  |  | N |
| LGPBANCO | Banco | I |  |  | N |
| LGPCODIGO | Código | I |  |  | N |
| LGPCONTA | Conta | S |  |  | N |
| LGPCONTRATO | Cod. Contrato | I |  |  | N |
| LGPDATADEPOSITO | Dt. Depósito | D |  |  | N |
| LGPDATAVENCTO | Dt. Vencimento | D |  |  | N |
| LGPDHCORRECAO | Dh. Correção | H |  |  | N |
| LGPVALORDEPOSITO | Vlr. Depósito | F |  |  | N |
| LGPVLRCORRIGIDO | Vlr. Corrigido | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimLocacao / [TIMLOC](TIMLOC.md) | TimLocacaoGarantiaCaucao / [TIMLGP](TIMLGP.md) | LOCCODIGO->LGPCONTRATO | I |
| TimLocacaoGarantiaCaucao / [TIMLGP](TIMLGP.md) | Banco / [TSIBCO](TSIBCO.md) | LGPBANCO->CODBCO | I |
| TimLocacaoGarantiaCaucao / [TIMLGP](TIMLGP.md) | TimLocacao / [TIMLOC](TIMLOC.md) | LGPCONTRATO->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
