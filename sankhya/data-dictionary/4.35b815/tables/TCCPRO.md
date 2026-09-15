# TCCPRO — Protocolo Civil

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProtocoloCivil | Protocolo Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| DOCUMENTO | Documento | S |  |  | N |
| DTRECEBI | Data Recebimento | H |  |  | N |
| ENTREGUE | Entregue | S |  |  | N |
| IDENTIDADE | Identidade | S |  |  | N |
| NUMPROTOCOLO | Nro. Protocolo | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PESSOA | Nome Pessoa | S |  |  | N |
| TIPO | Tipo Documento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | ProtocoloCivil / [TCCPRO](TCCPRO.md) | CODCC->CODCC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
