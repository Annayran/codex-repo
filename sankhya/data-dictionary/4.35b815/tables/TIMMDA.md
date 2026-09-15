# TIMMDA — Mídias

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimMidia | Mídias | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MDACODIGO | Código | I |  |  | N |
| MDAMIDIA | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimVeiculoComunicacao / [TIMVCO](TIMVCO.md) | TimMidia / [TIMMDA](TIMMDA.md) | VCOMIDIA->MDACODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
