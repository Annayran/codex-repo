# TIMCIT — TABLE TIMCIT

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIptuComissao | Comissão sobre IPTU | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CITCODIGO | Cod. Comissionamento | I |  |  | N |
| CITCOMAVISTA | Comissão a vista | F |  |  | N |
| CITINCORPAOVALOR | Incorporada ao valor | S |  |  | N |
| CITVIGENCIA | Dt. Limite | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIptuComissao / [TIMCIT](TIMCIT.md) | TimIptuComissaoFaixa / [TIMFCI](TIMFCI.md) | CITCODIGO->FCICOMISSAO | I |
| TimIptuComissaoFaixa / [TIMFCI](TIMFCI.md) | TimIptuComissao / [TIMCIT](TIMCIT.md) | FCICOMISSAO->CITCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
