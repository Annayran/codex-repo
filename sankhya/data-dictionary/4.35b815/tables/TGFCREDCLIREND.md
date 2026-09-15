# TGFCREDCLIREND — Tabela de declaração de renda do cliente

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RendaClienteCrediario | Declaração de renda do cliente | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLIENTE | Código cliente | I |  |  | N |
| CODRENDA | Código renda | I |  |  | N |
| DESCRRENDA | Descrição renda | S |  |  | N |
| DTCOMPROVACAO | Data comprovação | H |  |  | N |
| TIPRENDA | Tipo de renda | S |  |  | N |
| VLRRENDA | Valor de renda | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RendaClienteCrediario / [TGFCREDCLIREND](TGFCREDCLIREND.md) | ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | CODCLIENTE->CODCLIENTE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
