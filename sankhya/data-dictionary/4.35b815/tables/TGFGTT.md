# TGFGTT — Grupos de Tipo de Títulos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GruposTipoTitulo | Grupos de Tipo de Títulos | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODGRUPOTIPTIT | Grupo de Tipo de Título | I |  |  | N |
| DESCRGRUPOTIPTIT | Descrição | S |  |  | N |
| IMAGEM | Imagem | B |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TipoTitulo / [TGFTIT](TGFTIT.md) | GruposTipoTitulo / [TGFGTT](TGFGTT.md) | CODGRUPOTIPTIT->CODGRUPOTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
