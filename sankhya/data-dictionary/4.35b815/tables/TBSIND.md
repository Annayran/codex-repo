# TBSIND — TABLE TBSIND

- Módulo técnico: [TBS](../modules/TBS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| IndicadorEstrategicoBSC | Indicadores Estratégicos BSC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AREAS | Áreas | S |  |  | N |
| DESCFORM | Descrição Fórmula | S |  |  | N |
| DESCPARAM | Descrição do Paramêtro | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| NUEIXO | Eixo Estratégico | I |  |  | N |
| NUIND | Indicador Estratégico | I |  |  | N |
| UNMED | Unidade de Medição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| IndicadorEstrategicoBSC / [TBSIND](TBSIND.md) | EixoEstrategico / [TBSEIX](TBSEIX.md) | NUEIXO->NUEIXO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
