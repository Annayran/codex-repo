# TAPEQJ — Equipe por Projeto

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipePorProjeto | Equipe por Projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPROJ | CODPROJ | I |  |  | N |
| CODPROJFINAL | CODPROJFINAL | I |  |  | N |
| DHFINAL | DHFINAL | H |  |  | N |
| DHINICIAL | DHINICIO | H |  |  | N |
| NUEQUIPE | NUEQUIPE | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipePorProjeto / [TAPEQJ](TAPEQJ.md) | Equipe / [TCSEQP](TCSEQP.md) | NUEQUIPE->NUEQUIPE | I |
| EquipePorProjeto / [TAPEQJ](TAPEQJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
