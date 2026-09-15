# TCSTAC — Tipos de Ações

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TipoAcao | Tipos de Ações | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPACAO | Tipo Ação | I |  |  | N |
| DESCRACAO | Descrição | S |  |  | N |
| MANIPULADOR | Manipulador | S |  |  | N |
| NOMEACAO | Nome | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Alerta / [TCSALE](TCSALE.md) | TipoAcao / [TCSTAC](TCSTAC.md) | CODTIPACAO->CODTIPACAO | I |
| TipoAcao / [TCSTAC](TCSTAC.md) | Alerta / [TCSALE](TCSALE.md) | CODTIPACAO->CODTIPACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
