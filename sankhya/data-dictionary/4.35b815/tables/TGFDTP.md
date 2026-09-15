# TGFDTP — PrevisaoEntrega

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrevisaoEntrega | Previsão de Entrega | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| DTPREV | Data prevista | D |  |  | N |
| NUNOTA | Nota | I |  |  | N |
| QTD | Quantidade prevista | F |  |  | N |
| QTDENTREGUE | Qtd. Entregue | F |  |  | N |
| SEQPREV | Seq. Previsão | I |  |  | N |
| SEQUENCIA | Seq. Item | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemNota / [TGFITE](TGFITE.md) | PrevisaoEntrega / [TGFDTP](TGFDTP.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | PrevisaoEntrega / [TGFDTP](TGFDTP.md) | NUNOTAORIG->NUNOTA; SEQPREV->SEQPREV; SEQUENCIAORIG->SEQUENCIA | I |
| PrevisaoEntrega / [TGFDTP](TGFDTP.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| PrevisaoEntrega / [TGFDTP](TGFDTP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
