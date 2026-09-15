# TRSAVR — TRSAVR

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AvaliacaoRequisicao | Avaliação Requisição | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOTIVO | Cód. Motivo | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHAVALIACAO | Data Avaliação | H |  |  | N |
| NUAVALIACAO | Nro. Avaliação | I |  |  | N |
| NUREQUISICAO | Nro. Requisição | I |  |  | N |
| OBSAVALIACAO | Observação | S |  |  | N |
| STATUS | Status | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | AvaliacaoRequisicao / [TRSAVR](TRSAVR.md) | NUREQUISICAO->NUREQUISICAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
