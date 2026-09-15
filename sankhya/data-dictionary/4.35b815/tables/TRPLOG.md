# TRPLOG — Tabela de Log Replay

- Módulo técnico: [TRP](../modules/TRP.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LogReplay | Log Replay | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CONDICAO | Condição | S |  |  | N |
| DHPROC | Dt. Processo | H |  |  | N |
| ID | ID | I |  |  | N |
| NOMEARQUIVOBKP | Arquivo Backup | S |  |  | N |
| NOMEARQUIVOORI | Arquivo Originário | S |  |  | N |
| NUMOS | Núm. Ordem de Serviço/Ticket | I |  |  | N |
| TABELA | Tabela | S |  |  | N |
| TABELAGERADA | Tabela e campo gerados | S |  |  | N |
| TIPPROC | Tipo de processo | S |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
