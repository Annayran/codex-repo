# TIMFEI — Itens Fechamento

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFechamentoItens | Itens Fechamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FEICONSUMOAGUA | Consumo Água (m³) | F |  |  | N |
| FEICONSUMOENERGIA | Consumo Energia (Kwh) | I |  |  | N |
| FEICONSUMOGAS | Consumo Gás (m³) | F |  |  | N |
| FEICONTRATOLOC | Contrato Locação | I |  |  | N |
| FEIFECHAMENTO | Fechamento | I |  |  | N |
| FEIREFFORMAPGTO | Dt. Forma Pagamento | D |  |  | N |
| FEIVLRAGUA | Vlr. Água | F |  |  | N |
| FEIVLRENERGIA | Vlr. Energia | I |  |  | N |
| FEIVLRGAS | Vlr. Gás | I |  |  | N |
| FEIVLRIPTU | Vlr. IPTU | F |  |  | N |
| FEIVLRSEG | Vlr. Seguro | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Financeiro / [TGFFIN](TGFFIN.md) | TimFechamentoItens / [TIMFEI](TIMFEI.md) | TIMCONTRATOLOC->FEICONTRATOLOC; TIMFECHAMENTO->FEIFECHAMENTO | I |
| TimFechamento / [TIMFEC](TIMFEC.md) | TimFechamentoItens / [TIMFEI](TIMFEI.md) | FECCODIGO->FEIFECHAMENTO | I |
| TimFechamentoItens / [TIMFEI](TIMFEI.md) | TimFechamento / [TIMFEC](TIMFEC.md) | FEIFECHAMENTO->FECCODIGO | I |
| TimFechamentoItens / [TIMFEI](TIMFEI.md) | TimLocacao / [TIMLOC](TIMLOC.md) | FEICONTRATOLOC->LOCCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
