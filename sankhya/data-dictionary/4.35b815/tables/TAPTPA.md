# TAPTPA — Tipo de Paralisação

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoParalisacao | Tipo de Paralisação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAUSA | Causa | S |  |  | N |
| CODPAR | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ParalisacaoPorApontamento / [TAPPAA](TAPPAA.md) | TipoParalisacao / [TAPTPA](TAPTPA.md) | CODPAR->CODPAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
