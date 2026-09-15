# TGFJUM — Juros Mensais

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| JuroMensal | Juro Mensal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOEDA | Moeda | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| COTACAO | Cotação | F |  |  | N |
| DTALTER | Dt. Alteração | H |  |  | N |
| FIMMES | Fim mês | S |  |  | N |
| INDICE | Índice | F |  |  | N |
| NUFIN | Nro Financeiro | I |  |  | N |
| REFERENCIA | Referência | H |  |  | N |
| SINAL | Sinal | I |  |  | N |
| VLRJURO | Vlr. Juro | F |  |  | N |
| VLRJUROMES | Vlr. Juro mês | F |  |  | N |
| VLRJUROMES2 | Vlr. Juro mês 2 | F |  |  | N |
| VLRMULTA | Vlr. Multa | F |  |  | N |
| VLRMULTAMES | Vlr. Multa mês | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| JuroMensal / [TGFJUM](TGFJUM.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| JuroMensal / [TGFJUM](TGFJUM.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| JuroMensal / [TGFJUM](TGFJUM.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
