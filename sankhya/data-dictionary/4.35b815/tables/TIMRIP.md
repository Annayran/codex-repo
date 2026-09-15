# TIMRIP — TABLE TIMRIP

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptuReajuste | Reajuste de IPTU | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| RIPCODIGO | Cod. Reajuste | I |  |  | N |
| RIPDESCONTO | Desconto (%) | F |  |  | N |
| RIPDHALTER | Dh. Alteração | H |  |  | N |
| RIPDHINC | Dh. Inclusão | H |  |  | N |
| RIPESTAGIO | Estágio | S |  |  | N |
| RIPPERCREAJUSTE | Reajuste (%) | F |  |  | N |
| RIPREFERENCIA | Ano de referência | D |  |  | N |
| RIPREFINICOBRANCA | Mês de referência p/ cobrança | D |  |  | N |
| RIPREFINIPAGTO | Referência p/ pagamento | D |  |  | N |
| RIPTXEXPEDIENTE | Tx Expediente | F |  |  | N |
| RIPUSUALTER | Usuário de Alteração | I |  |  | N |
| RIPUSUINC | Usuário de Inclusão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIptuReajuste / [TIMRIP](TIMRIP.md) | TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | RIPCODIGO->RICREAJUSTE | I |
| TimIptuReajuste / [TIMRIP](TIMRIP.md) | Usuario / [TSIUSU](TSIUSU.md) | RIPUSUINC->CODUSU | I |
| TimIptuReajuste / [TIMRIP](TIMRIP.md) | Usuario2 / [TSIUSU](TSIUSU.md) | RIPUSUALTER->CODUSU | I |
| TimIptuReajusteImovel / [TIMRIC](TIMRIC.md) | TimIptuReajuste / [TIMRIP](TIMRIP.md) | RICREAJUSTE->RIPCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
