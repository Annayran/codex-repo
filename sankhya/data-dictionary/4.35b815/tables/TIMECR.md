# TIMECR — Custos p/ Condomínio

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCustosCond | Custos p/ Condomínio | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ECREMPREENDIMENTO | Empreendimento | I |  |  | N |
| ECRREFERENCIA | Referência | D |  |  | N |
| ECRTIPO | Tipo | S |  |  | N |
| ECRVALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | TimCustosCond / [TIMECR](TIMECR.md) | EPRCODIGO->ECREMPREENDIMENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
