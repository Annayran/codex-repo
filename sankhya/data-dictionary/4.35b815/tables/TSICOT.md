# TSICOT — Cotação de Valor para Moeda

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CotacaoMoeda | Cotação de Moeda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOEDA | Cód.Moeda | I |  |  | N |
| COTACAO | Cotação | F |  |  | N |
| DTMOV | Data do movimento | D |  |  | N |
| INDICE2 | Índice 2 | F |  |  | N |
| INDICEDESC | Índice Desconto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CotacaoMoeda / [TSICOT](TSICOT.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| Moeda / [TSIMOE](TSIMOE.md) | CotacaoMoeda / [TSICOT](TSICOT.md) | CODMOEDA->CODMOEDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
