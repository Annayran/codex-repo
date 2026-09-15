# TGFRCB — Régua de Cobrança

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
| ReguaCobranca | Régua de Cobrança | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODREGUA | Código | I |  |  | N |
| CONFIG | CONFIG | C |  |  | N |
| DESCREGUA | Descrição | S |  |  | N |
| DHALTER | Data de alteração | H |  |  | N |
| DHATIVA | Data de ativação | H |  |  | N |
| DHINC | Data de inclusão | H |  |  | N |
| DTDESATAUT | Data de desativação automática | H |  |  | N |
| HORAEXEC | Horário execução | T |  |  | N |
| ULTEXECUCAO | Última execução | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExecReguaCobranca / [TGFEXR](TGFEXR.md) | ReguaCobranca / [TGFRCB](TGFRCB.md) | CODREGUA->CODREGUA | I |
| ReguaCobranca / [TGFRCB](TGFRCB.md) | EventosCobranca / [TGFECB](TGFECB.md) | CODREGUA->CODREGUA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
