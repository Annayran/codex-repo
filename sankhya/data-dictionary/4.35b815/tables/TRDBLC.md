# TRDBLC — BloqueioControle

- Módulo técnico: [TRD](../modules/TRD.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| BloqueioControle | Bloqueio controle | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | CODUSU | I |  |  | N |
| DHINC | DHINC | H |  |  | N |
| MOTIVO | MOTIVO | S |  |  | N |
| RESOURCEID | RESOURCEID | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| BloqueioControle / [TRDBLC](TRDBLC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
