# TSIESTATSERVICO — Estatísticas de chamada de serviço

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EstatisticaServicoSistema | Estatísticas de chamada de serviço | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Usuário | I |  |  | N |
| DTACESSO | Dt. Acesso | D |  |  | N |
| HASH | Hash | S |  |  | N |
| QTD | Qtd. acesso | I |  |  | N |
| RESOURCEID | ResourceID | S |  |  | N |
| SERVICO | Nome serviço | S |  |  | N |
| TEMPOTOTAL | Qtd. acesso | I |  |  | N |
| TMAIOR | Maior tempo | I |  |  | N |
| TMEDIO | Tempo médio | I |  |  | N |
| TMENOR | Menor tempo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EstatisticaServicoSistema / [TSIESTATSERVICO](TSIESTATSERVICO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
