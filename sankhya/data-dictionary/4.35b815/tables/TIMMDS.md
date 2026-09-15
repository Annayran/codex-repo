# TIMMDS — TABLE TIMMDS

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
| TimMotivoDesistencia | Motivo de Desistência da Proposta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| MDSCODIGO | Código | I |  |  | N |
| MDSMOTIVO | Motivo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimImovelFAC / [TIMIPR](TIMIPR.md) | TimMotivoDesistencia / [TIMMDS](TIMMDS.md) | IPRMTVDESIST->MDSCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
