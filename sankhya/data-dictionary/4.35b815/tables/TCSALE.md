# TCSALE — Alertas

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Alerta | Alertas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPACAO | Tipo Ação | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRALE | Descrição do Alerta | S |  |  | N |
| NUALE | Número do Alerta | I |  |  | N |
| NUFAP | Número da FAP | I |  |  | N |
| NUMETAPA | Número da Etapa | I |  |  | N |
| OPERADOR | Operador | S |  |  | N |
| QUEM | Quem | S |  |  | N |
| TIPVALOR | Tipo de valor | S |  |  | N |
| TIQUEM | Quem | S |  |  | N |
| VALOR | Valor | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Alerta / [TCSALE](TCSALE.md) | EtapaProjeto / [TCSFET](TCSFET.md) | NUFAP->NUFAP; NUMETAPA->NUMETAPA | I |
| Alerta / [TCSALE](TCSALE.md) | TipoAcao / [TCSTAC](TCSTAC.md) | CODTIPACAO->CODTIPACAO | I |
| TipoAcao / [TCSTAC](TCSTAC.md) | Alerta / [TCSALE](TCSALE.md) | CODTIPACAO->CODTIPACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
