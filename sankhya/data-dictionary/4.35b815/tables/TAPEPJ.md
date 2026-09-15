# TAPEPJ — Equipamento por Projeto

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipamentoProjeto | Equipamento por Projeto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | CODBEM | S |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| CODPROJ | CODPROJ | I |  |  | N |
| CODPROJFINAL | CODPROJFINAL | I |  |  | N |
| DHFINAL | DHFINAL | H |  |  | N |
| DHINICIAL | DHINICIAL | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipamentoProjeto / [TAPEPJ](TAPEPJ.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| EquipamentoProjeto / [TAPEPJ](TAPEPJ.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| EquipamentoProjeto / [TAPEPJ](TAPEPJ.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
