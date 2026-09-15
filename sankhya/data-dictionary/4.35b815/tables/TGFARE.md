# TGFARE — Análise de Rentabilidade

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnaliseRentabilidade | Análise de Rentabilidade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUNOTA | Número Único da Nota | I |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| VLRCPA | Valor da Compra | F |  |  | N |
| VLRCUSTO | Calor de Custo | F |  |  | N |
| VLRUNIT | Valor Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnaliseRentabilidade / [TGFARE](TGFARE.md) | ItemNota / [TGFITE](TGFITE.md) | NUNOTA->NUNOTA; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
