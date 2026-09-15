# TIMFEC — Fechamento Condomínio

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimFechamento | Fechamento Condomínio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| FECCODIGO | Código | I |  |  | N |
| FECDESPRATEAVEL | Despesa Rateável | F |  |  | N |
| FECEMPREENDIMENTO | Empreendimento | I |  |  | N |
| FECGEROUPARC | Parcelas Geradas | S |  |  | N |
| FECPRECOAGUA | Preço Água | F |  |  | N |
| FECPRECOENERGIA | Preço Energia | F |  |  | N |
| FECPRECOGAS | Preço Gás | F |  |  | N |
| FECPRECOIPTU | Preço IPTU | F |  |  | N |
| FECPRECOSEGURO | Preço Seguro | F |  |  | N |
| FECREFERENCIA | Referência | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimFechamento / [TIMFEC](TIMFEC.md) | TimEmpreendimento / [TIMEPR](TIMEPR.md) | FECEMPREENDIMENTO->EPRCODIGO | I |
| TimFechamento / [TIMFEC](TIMFEC.md) | TimFechamentoItens / [TIMFEI](TIMFEI.md) | FECCODIGO->FEIFECHAMENTO | I |
| TimFechamentoItens / [TIMFEI](TIMFEI.md) | TimFechamento / [TIMFEC](TIMFEC.md) | FEIFECHAMENTO->FECCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
