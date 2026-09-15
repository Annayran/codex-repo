# TIMTXC — Taxas e Comissões de Venda de Lt.

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimTaxasVendas | Taxas e Comissões de Venda de Lt. | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| TXCCODCENCUS | Centro de Resultado | I |  |  | N |
| TXCCODCTABCOINT | Conta | I |  |  | N |
| TXCCODEMP | Empresa | I |  |  | N |
| TXCCODIGO | Código | I |  |  | N |
| TXCCODNAT | Natureza | I |  |  | N |
| TXCCODPROJ | Projeto | I |  |  | N |
| TXCCODTIPOPER | Tipo de Operação | I |  |  | N |
| TXCCODTIPTIT | Tipo de Título | I |  |  | N |
| TXCDESCRICAO | Descrição | S |  |  | N |
| TXCLOTEAMENTO | Loteamento | I |  |  | N |
| TXCNROPARCELA | Nro. Parcela | I |  |  | N |
| TXCORIGEMPARCEIRO | Origem Parceiro | S |  |  | N |
| TXCPARCEIRO | Parceiro | I |  |  | N |
| TXCPORFAIXA | Taxa Por Faixa | S |  |  | N |
| TXCQTDPARCELA | Qtd. Mínima Parcelas | I |  |  | N |
| TXCQTDPARCELAMAX | Qtd. Máxima Parcelas | I |  |  | N |
| TXCRECDESP | Receita/Despesa | I |  |  | N |
| TXCTAXALOTE | Taxa % | F |  |  | N |
| TXCTAXASOBRE | Taxa Sobre | S |  |  | N |
| TXCTAXAVALOR | Valor Taxa | F |  |  | N |
| TXCTIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | CentroResultado / [TSICUS](TSICUS.md) | TXCCODCENCUS->CODCENCUS | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | ContaBancaria / [TSICTA](TSICTA.md) | TXCCODCTABCOINT->CODCTABCOINT | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Empresa / [TSIEMP](TSIEMP.md) | TXCCODEMP->CODEMP | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Natureza / [TGFNAT](TGFNAT.md) | TXCCODNAT->CODNAT | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Parceiro / [TGFPAR](TGFPAR.md) | TXCPARCEIRO->CODPARC | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | Projeto / [TCSPRJ](TCSPRJ.md) | TXCCODPROJ->CODPROJ | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | TimLoteamento / [TIMLOT](TIMLOT.md) | TXCLOTEAMENTO->LOTCODIGO | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | TXCCODTIPOPER->CODTIPOPER | I |
| TimTaxasVendas / [TIMTXC](TIMTXC.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TXCCODTIPTIT->CODTIPTIT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
