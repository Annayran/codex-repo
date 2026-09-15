# TGPPRC — TABLE TGPPRC

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Procedimentos | Procedimentos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROCEDIMENTO | Cód. Procedimentos | I |  |  | N |
| CODUSU | CODUSU | I |  |  | N |
| DESCRPROCEDIMENTO | Descrição | S |  |  | N |
| DHALTER | DHALTER | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Procedimentos / [TGPPRC](TGPPRC.md) | Anexo / [TSIATA](TSIATA.md) | CODPROCEDIMENTO->CODATA | I |
| ProcedimentosTarefas / [TGPTPR](TGPTPR.md) | Procedimentos / [TGPPRC](TGPPRC.md) | CODPROCEDIMENTO->CODPROCEDIMENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
