# TSIFER — Feriado

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Feriado | Feriado | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCID | Cód. Cidade | I |  |  | N |
| CODPAIS | País | I |  |  | N |
| CODUF | Estado | I |  |  | N |
| DESCRFERIADO | Descrição | S |  |  | N |
| DTFERIADO | Data do feriado | D |  |  | N |
| NACIONAL | Tipo | S |  |  | N |
| OBRIGATORIO | Obrigatório | S |  |  | N |
| RECORRENTE | Recorrente | S |  |  | N |
| USANOPONTO | Usar no Ponto/Pessoal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Feriado / [TSIFER](TSIFER.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| Feriado / [TSIFER](TSIFER.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| Feriado / [TSIFER](TSIFER.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
