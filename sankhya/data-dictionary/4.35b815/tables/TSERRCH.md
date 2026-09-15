# TSERRCH — Cheque e Recheque

- Módulo técnico: [TSE](../modules/TSE.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 0
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ChequeRecheque | ChequeRecheque | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGENCIA | Agência | I |  |  | N |
| BANCO | Banco | I |  |  | N |
| CHQFINAL | Número do Cheque Final | I |  |  | N |
| CHQINICIAL | Número do Cheque Inicial | I |  |  | N |
| CTACORRENTE | Número da Conta Corrente | I |  |  | N |
| DTABERTURADACTA | Data de Abertura da Conta Corrente | H |  |  | N |
| MOTIVO | Motivo da Restrição do Cheque | S |  |  | N |
| MSGSITUACTA | Mensagem da Situação da Conta Corrente | S |  |  | N |
| NOMECORRENT | Nome do Correntista | S |  |  | N |
| NUCONSULTA | NUCONSULTA | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

Nenhuma relação DWF encontrada nesta exportação.

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
