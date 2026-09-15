# TIMQPV — Parcelamento

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimParcelamento | Forma Pagamento Venda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| QPVCONTRATO | Contrato de Venda | I |  |  | N |
| QPVDATAPRIMEIRA | Dt. Venc. Primeira Parc. | D |  |  | N |
| QPVFORMAPAGAMENTO | Forma de Pagamento | S |  |  | N |
| QPVIDENTIFICADOR | Sequencia | I |  |  | N |
| QPVINDICE | Índice até chaves | I |  |  | N |
| QPVINDICEAPCHV | Índice após chaves | I |  |  | N |
| QPVPLANO | Plano | I |  |  | N |
| QPVQUANTIDADE | Qtd. Parcelas | I |  |  | N |
| QPVTIPO | Periodiciadade Parcelas | S |  |  | N |
| QPVTIPOPARCELA | Tipo | S |  |  | N |
| QPVVALORPARCELA | Vlr Parcela | F |  |  | N |
| QPVVALORPARCELADO | Vlr. Total | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimParcelamento / [TIMQPV](TIMQPV.md) | CLACODIGO->QPVCONTRATO | I |
| TimParcelamento / [TIMQPV](TIMQPV.md) | Moeda / [TSIMOE](TSIMOE.md) | QPVINDICE->CODMOEDA | I |
| TimParcelamento / [TIMQPV](TIMQPV.md) | Moeda2 / [TSIMOE](TSIMOE.md) | QPVINDICEAPCHV->CODMOEDA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
