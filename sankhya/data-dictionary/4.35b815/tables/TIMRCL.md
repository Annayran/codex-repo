# TIMRCL — Rateio das Comissões

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimRateioComisaoVenda | Rateio das Comissões de Venda | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RCLAPLICARSOBRE | Base Cálculo | S |  |  | N |
| RCLCONTRATO | Contrato de Venda | I |  |  | N |
| RCLCORRETOR | Corretor | I |  |  | N |
| RCLDTPAGAMENTO | Dt. Pagamento | D |  |  | N |
| RCLDTPREVISTO | Dt. Previsão Pgto. | D |  |  | N |
| RCLEMITENTE | Emitente | I |  |  | N |
| RCLIDENTIFICADOR | Sequencia | I |  |  | N |
| RCLNOTAFISCAL | Nota Fiscal | I |  |  | N |
| RCLOBSERVACAO | Observação | S |  |  | N |
| RCLORIGEM | Tipo Pgto. | S |  |  | N |
| RCLPERCENTUAL | Percentual | F |  |  | N |
| RCLPESSOA | Atuação | S |  |  | N |
| RCLVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | CLACODIGO->RCLCONTRATO | I |
| TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | CabecalhoNota / [TGFCAB](TGFCAB.md) | RCLNOTAFISCAL->NUNOTA | I |
| TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | TimContratoVenda / [TIMCLA](TIMCLA.md) | RCLCONTRATO->CLACODIGO | I |
| TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | TimCorretor / [TIMCOR](TIMCOR.md) | RCLCORRETOR->CORCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
