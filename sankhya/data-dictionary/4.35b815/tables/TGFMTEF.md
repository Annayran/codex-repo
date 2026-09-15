# TGFMTEF — Monitoria TEF

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MonitoriaTEF | MonitoriaTEF | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| CONFIRMACAO | Confirmação | S |  |  | N |
| DESFAZIMENTO | Desfazimento | S |  |  | N |
| DHALTER | Data Alteração | H |  |  | N |
| GATEWAY | Integradora | S |  |  | N |
| HOSTNAME | Maquina | S |  |  | N |
| IDMTEF | Sequencia | I |  |  | N |
| NT_USERNAME | Usuário da maquina | S |  |  | N |
| NUMNSU | Numero Unico NSU. | S |  |  | N |
| OPERACAO | Operação | I |  |  | N |
| PROGRAMA | Programa | S |  |  | N |
| REQUEST | Request | C |  |  | N |
| RESPONSE | Response | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MonitoriaTEF / [TGFMTEF](TGFMTEF.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
