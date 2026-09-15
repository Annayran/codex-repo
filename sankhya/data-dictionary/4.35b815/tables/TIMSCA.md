# TIMSCA — Acompanhamento SAC

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimAtendClienteAcompanhamento | Acompanhamentos SAC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| SCACODIGO | Código | I |  |  | N |
| SCADATARES | Dh. Inclusão | H |  |  | N |
| SCAHORAFIM | Dt. Término | H |  |  | N |
| SCAHORAINICIO | Dt. Início | H |  |  | N |
| SCAPROMETIDO | Dt. Prevista | D |  |  | N |
| SCARESULTADO | Acompanhamento | S |  |  | N |
| SCASAC | SAC | I |  |  | N |
| SCASOLICITACAO | Solicitação | S |  |  | N |
| SCAUSUARIO | Usuário Inclusão | I |  |  | N |
| SCAUSUDEST | Usuário Destino | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | SCASAC->SACCODIGO | I |
| TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | Usuario / [TSIUSU](TSIUSU.md) | SCAUSUARIO->CODUSU | I |
| TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | Usuario2 / [TSIUSU](TSIUSU.md) | SCAUSUDEST->CODUSU | I |
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimAtendClienteAcompanhamento / [TIMSCA](TIMSCA.md) | SACCODIGO->SCASAC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
