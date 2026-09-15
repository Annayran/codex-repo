# TIMINP — TIMINP

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimIntegracaoPortal | Integrações Portais | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| INPCODIGO | Código | I |  |  | N |
| INPMODULOJAVA | Módulo Java | I |  |  | N |
| INPNOMEINTEGRACAO | Descrição | S |  |  | N |
| INPVIEW | Nome da View | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimIntegracaoPortal / [TIMINP](TIMINP.md) | ModuloAdicional / [TSIMOD](TSIMOD.md) | INPMODULOJAVA->CODMODULO | I |
| TimIntegracaoPortal / [TIMINP](TIMINP.md) | TimPortal / [TIMIPO](TIMIPO.md) | INPCODIGO->IPOINTEGRACAO | I |
| TimPortal / [TIMIPO](TIMIPO.md) | TimIntegracaoPortal / [TIMINP](TIMINP.md) | IPOINTEGRACAO->INPCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
