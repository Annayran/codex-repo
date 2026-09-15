# TMDGRV — Tabela de ligação Variável - Grupo de variáveis

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDGrupoVariavel | Variáveis no Grupo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Código do grupo | I |  |  | N |
| CODVAR | Código da variável | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDGrupo / [TMDGRU](TMDGRU.md) | MSDGrupoVariavel / [TMDGRV](TMDGRV.md) | CODGRUPO->CODGRUPO | I |
| MSDGrupoVariavel / [TMDGRV](TMDGRV.md) | MSDVariavel / [TMDVAR](TMDVAR.md) | CODVAR->CODVAR | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | MSDGrupoVariavel / [TMDGRV](TMDGRV.md) | CODVAR->CODVAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
