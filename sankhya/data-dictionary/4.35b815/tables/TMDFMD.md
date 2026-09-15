# TMDFMD — Tabela de Fila de Contatos de Mensagens

- Módulo técnico: [TMD](../modules/TMD.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MSDDestFilaMensagem | Contatos destinatários da fila de mensagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFILA | CODFILA | I |  |  | N |
| EMAIL | EMAIL | S |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MSDDestFilaMensagem / [TMDFMD](TMDFMD.md) | MSDFilaMensagem / [TMDFMG](TMDFMG.md) | CODFILA->CODFILA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
