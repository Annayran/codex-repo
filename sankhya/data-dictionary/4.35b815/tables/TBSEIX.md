# TBSEIX — Eixos Estratégicos

- Módulo técnico: [TBS](../modules/TBS.md)
- Campos: 3
- Instâncias DWF: 2
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EixoEstrategico | Eixo Estratégico | S | N |
| EixoEstrategicoFilha | Eixo Estratégico Instância Filha | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| NUEIXO | Eixo Estratégico | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| IndicadorEstrategicoBSC / [TBSIND](TBSIND.md) | EixoEstrategico / [TBSEIX](TBSEIX.md) | NUEIXO->NUEIXO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
