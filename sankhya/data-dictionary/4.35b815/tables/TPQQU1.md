# TPQQU1 — Questionário

- Módulo técnico: [TPQ](../modules/TPQ.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| QuestionarioVeiculo | Questionário | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODQUEST | Cód. Questionário | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRQUEST | Descrição | S |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| NOTAMIN | Nota Mínima | I |  |  | N |
| PODEFECHARORDCARG | Pode fechar ordem de carga | S |  |  | N |
| PODEOBS | Obs | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Veiculo / [TGFVEI](TGFVEI.md) | QuestionarioVeiculo / [TPQQU1](TPQQU1.md) | CODQUEST->CODQUEST | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
