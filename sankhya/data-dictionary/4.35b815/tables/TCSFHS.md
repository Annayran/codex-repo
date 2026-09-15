# TCSFHS — Histórico Status de Projeto

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| HistoricoStatusProjeto | Histórico do Status do Projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | DHALTER | H |  |  | N |
| NUFAP | NUFAP | I |  |  | N |
| STATUS | STATUS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| HistoricoStatusProjeto / [TCSFHS](TCSFHS.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ProjetoServico / [TCSFAP](TCSFAP.md) | HistoricoStatusProjeto / [TCSFHS](TCSFHS.md) | NUFAP->NUFAP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
