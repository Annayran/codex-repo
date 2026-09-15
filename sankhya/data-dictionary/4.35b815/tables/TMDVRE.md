# TMDVRE — Tabela de variáveis resolvidas

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDVarsResolvidas | Tabela de Variáveis Resolvidas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODVAR | Código da variável | I |  |  | N |
| CODVARRES | Código da variável resolvida | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| DTREF | Data de referência | H |  |  | N |
| VALOR | Valor | S |  |  | N |
| VALORNUMERICO | VALORNUMERICO | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDVarsResolvidas / [TMDVRE](TMDVRE.md) | MSDVariavel / [TMDVAR](TMDVAR.md) | CODVAR->CODVAR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
