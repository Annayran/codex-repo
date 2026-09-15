# TGFCREDAUDIT — Tabela de auditoria de crediário

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AuditoriaCadastroClienteCred | Auditoria Cadastro Cliente Crediario | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCLIENTE | Código do cliente | I |  |  | N |
| CODUSUALTER | Código do usuário que realizou a alteração | I |  |  | N |
| DHALTER | Data e hora de alteração do campo | H |  |  | N |
| NOMECAMPO | Nome do campo alterado | S |  |  | N |
| SEQUENCIA | Sequência de alteração do campo | I |  |  | N |
| VLRANTERIOR | Valor anterior à alteração | S |  |  | N |
| VLRNOVO | Novo valor do campo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AuditoriaCadastroClienteCred / [TGFCREDAUDIT](TGFCREDAUDIT.md) | ClienteCrediario / [TGFCREDCLI](TGFCREDCLI.md) | CODCLIENTE->CODCLIENTE | I |
| AuditoriaCadastroClienteCred / [TGFCREDAUDIT](TGFCREDAUDIT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
