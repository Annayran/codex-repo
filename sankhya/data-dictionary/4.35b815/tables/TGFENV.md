# TGFENV — Email de cotação enviados

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmailCotacaoEnviada | Email de cotação enviados | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data e hora alteração | H |  |  | N |
| EMAIL | Email | S |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmailCotacaoEnviada / [TGFENV](TGFENV.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | NUNOTA->NUNOTA | I |
| EmailCotacaoEnviada / [TGFENV](TGFENV.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
