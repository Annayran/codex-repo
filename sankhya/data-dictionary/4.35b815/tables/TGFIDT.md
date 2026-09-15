# TGFIDT — Ítens Notas Datas

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemNotaData | Ítens Notas Datas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPDT | Tipo Data | I |  |  | N |
| DTFIM | Data Fim | H |  |  | N |
| DTINICIO | Data Início | H |  |  | N |
| NUNOTA | Número único Nota | I |  |  | N |
| OBS | Observação | S |  |  | N |
| QTDMEDIDA | Quantidade Medida | F |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ItemNotaData / [TGFIDT](TGFIDT.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |
| ItemNotaData / [TGFIDT](TGFIDT.md) | TipoData / [TGFTDT](TGFTDT.md) | CODTIPDT->CODTIPDT | I |
| TipoData / [TGFTDT](TGFTDT.md) | ItemNotaData / [TGFIDT](TGFIDT.md) | CODTIPDT->CODTIPDT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
