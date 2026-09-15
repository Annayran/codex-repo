# TAPMDO — Metadado objeto projeto

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MetadadoObjetoProjeto | Metadados objeto projeto | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORMTOP | CODFORMTOP | I |  |  | N |
| CODFORMVAL | CODFORMVAL | I |  |  | N |
| DIMENSAO | DIMENSAO | S |  |  | N |
| NOMETAB | NOMETAB | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MetadadoObjetoProjeto / [TAPMDO](TAPMDO.md) | Formula / [TSIFOR](TSIFOR.md) | CODFORMTOP->CODFORM | I |
| MetadadoObjetoProjeto / [TAPMDO](TAPMDO.md) | Formula2 / [TSIFOR](TSIFOR.md) | CODFORMVAL->CODFORM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
