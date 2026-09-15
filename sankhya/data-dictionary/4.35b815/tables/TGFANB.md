# TGFANB — Antecipação de recebíveis

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AntecipacaoRecebiveis | Antecipação de Recebíveis | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DOCANTBANC | Nro. documento | S |  |  | N |
| DTANTBANC | Dt. Antecipação | D |  |  | N |
| NUANTBANC | Nro. único | I |  |  | N |
| NUFINDESPADIC | Despesa adicional | I |  |  | N |
| NUFINDESPOP | Demais Despesas Operacionais | I |  |  | N |
| NUFINTITBAN | Antecipação | I |  |  | N |
| NUFINTITIOF | Despesa com IOF | I |  |  | N |
| NUFINTITJUR | Taxas de Antecipação | I |  |  | N |
| NUFINTITOBR | Obrigação ao Banco | I |  |  | N |
| NUFINTITORI | Título Original | I |  |  | N |
| STATUSANT | Status antecipação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AntecipacaoRecebiveis / [TGFANB](TGFANB.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFINTITORI->NUFIN | I |
| Financeiro / [TGFFIN](TGFFIN.md) | AntecipacaoRecebiveis / [TGFANB](TGFANB.md) | NUFIN->NUFINTITORI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
