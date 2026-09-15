# TMIPXM — Plano de ação x Meta

- Módulo técnico: [TMI](../modules/TMI.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PlanoAcaoMeta | Plano de ação x Meta | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPLA | Cód. Plano Ação | I |  |  | N |
| NUMET | Cód. Meta | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PlanoAcao / [TMIPLA](TMIPLA.md) | PlanoAcaoMeta / [TMIPXM](TMIPXM.md) | CODPLA->CODPLA | I |
| PlanoAcaoMeta / [TMIPXM](TMIPXM.md) | MetaGestaoIndicadores / [TMIMET](TMIMET.md) | NUMET->NUMET | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
