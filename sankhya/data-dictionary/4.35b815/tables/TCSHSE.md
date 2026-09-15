# TCSHSE — Histórico de Suplementações

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoSuplementacao | Histórico de Suplementação | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Dt. alteração | H |  |  | N |
| NUFAP | Nro único FAP | I |  |  | N |
| NUMETAPADEST | Etapa destino | I |  |  | N |
| NUMETAPAORIG | Etapa origem | I |  |  | N |
| QTDHORAS | Qtd. horas | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoSuplementacao / [TCSHSE](TCSHSE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPAORIG->NUMETAPA | I |
| HistoricoSuplementacao / [TCSHSE](TCSHSE.md) | EtapaProjeto2 / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPADEST->NUMETAPA | I |
| HistoricoSuplementacao / [TCSHSE](TCSHSE.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
