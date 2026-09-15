# TSILBO — Limite de liberação de bonificação

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LimiteLiberacaoBonificacao | Limite de liberação de bonificação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODUSU | Cód. Usuário | I |  |  | N |
| DTFINAL | Data Final | D |  |  | N |
| DTINICIAL | Data Inicial | D |  |  | N |
| EVENTO | Evento | I |  |  | N |
| NULBO | Número liberação de bonificação | I |  |  | N |
| VLRLIMITE | Limite Bonificação | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LimiteLiberacaoBonificacao / [TSILBO](TSILBO.md) | LiberacaoLimite / [TSILIB](TSILIB.md) | NULBO->NULBO | I |
| LimiteLiberacaoBonificacao / [TSILBO](TSILBO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
