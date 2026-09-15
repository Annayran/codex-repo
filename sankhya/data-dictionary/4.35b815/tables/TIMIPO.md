# TIMIPO — TIMIPO

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
| TimPortal | Portais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| IPOCLASSNAME | Class Name | S |  |  | N |
| IPOCODIGO | Código | I |  |  | N |
| IPOINTEGRACAO | Integração | I |  |  | N |
| IPONOMEPORTAL | Nome Portal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIntegracaoPortal / [TIMINP](TIMINP.md) | TimPortal / [TIMIPO](TIMIPO.md) | INPCODIGO->IPOINTEGRACAO | I |
| TimPortal / [TIMIPO](TIMIPO.md) | TimIntegracaoPortal / [TIMINP](TIMINP.md) | IPOINTEGRACAO->INPCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
