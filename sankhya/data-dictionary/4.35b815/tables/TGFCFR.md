# TGFCFR — Tabela Calculo de Frete

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TabelaCalculoFrete | Tabela Cálculo de Frete | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CRITERIORATFRETE | Rateio frete | S |  |  | N |
| DESCRCALCFRET | Descrição | S |  |  | N |
| NUCFR | Cod. Cálculo de frete | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| SERVICOECT | Serviço Correios | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoNota / [TGFCAB](TGFCAB.md) | TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | NUCFR->NUCFR | I |
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | RegiaoCalculoFrete / [TGFCFRC](TGFCFRC.md) | NUCFR->NUCFR | I |
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | RotaCalculoFrete / [TGFRCF](TGFRCF.md) | NUCFR->NUCFR | I |
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | ServicoCorreios / [TGFECT](TGFECT.md) | SERVICOECT->CODSERVICO | I |
| TabelaCalculoFrete / [TGFCFR](TGFCFR.md) | TransportadoraCalculoFrete / [TGFCFP](TGFCFP.md) | NUCFR->NUCFR | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
