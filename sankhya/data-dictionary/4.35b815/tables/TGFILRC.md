# TGFILRC — Item de Log de processamento cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemLogProcessamentoCartao | Item de log de processamento cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMSG | Cód. mensagem | S |  |  | N |
| DTMOV | Dt. transação | D |  |  | N |
| MSG | Mensagem | C |  |  | N |
| NROLINHA | Nro. linha | I |  |  | N |
| NUFIN | Nro. único financeiro | I |  |  | N |
| NULRC | Nro. único | I |  |  | N |
| NUMNSU | NSU | S |  |  | N |
| SEQLRC | Sequência | I |  |  | N |
| VLRPARCELA | Vlr. parcela | F |  |  | N |
| VLRTRANSACAO | Vlr. transação | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemLogProcessamentoCartao / [TGFILRC](TGFILRC.md) | LogProcessamentoCartao / [TGFLRC](TGFLRC.md) | NULRC->NULRC | I |
| LogProcessamentoCartao / [TGFLRC](TGFLRC.md) | ItemLogProcessamentoCartao / [TGFILRC](TGFILRC.md) | NULRC->NULRC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
