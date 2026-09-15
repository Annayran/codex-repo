# TGFSTA — Status do Pedido

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| StatusPedido | Status do Pedido | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODETAPA | Etapa | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCROPER | Descrição | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| NUNOTAORIG | Número único Nota Origem | I |  |  | N |
| SEQOTICA | Sequência Ótica | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProducao / [TGFETA](TGFETA.md) | StatusPedido / [TGFSTA](TGFSTA.md) | CODETAPA->CODETAPA | I |
| StatusPedido / [TGFSTA](TGFSTA.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| StatusPedido / [TGFSTA](TGFSTA.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| StatusPedido / [TGFSTA](TGFSTA.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
