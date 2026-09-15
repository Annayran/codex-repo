# TMDGRU — Tabela de grupos de variáveis

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDGrupo | Tabela de grupo de variáveis | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPO | Código do Grupo | I |  |  | N |
| DESCRGRUPO | Descrição | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| ORDEM | Ordem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDGrupo / [TMDGRU](TMDGRU.md) | MSDGrupoVariavel / [TMDGRV](TMDGRV.md) | CODGRUPO->CODGRUPO | I |
| MSDVariavel / [TMDVAR](TMDVAR.md) | MSDGrupo / [TMDGRU](TMDGRU.md) | CODGRUPO->CODGRUPO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
