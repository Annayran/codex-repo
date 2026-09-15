# TGFCPC — Conta Parceiro Convênio

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConvenioContaParceiro | Convênio da Conta de Parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CONVENIO | Convênio | I |  |  | N |
| DTALTER | Data alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConvenioContaParceiro / [TGFCPC](TGFCPC.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConvenioContaParceiro / [TGFCPC](TGFCPC.md) | ConvenioConta / [TGFCOC](TGFCOC.md) | CODCTABCOINT->CODCTABCOINT; CONVENIO->CONVENIO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | ConvenioContaParceiro / [TGFCPC](TGFCPC.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
