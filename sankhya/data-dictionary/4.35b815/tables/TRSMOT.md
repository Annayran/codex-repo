# TRSMOT — Motivos para Requisição

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotivoRequisicao | Motivo para Requisição | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODMOTIVO | Código | I |  |  | N |
| DESCRMOTIVO | Descrição | S |  |  | N |
| TIPMOTIVO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | MotivoRequisicao / [TRSMOT](TRSMOT.md) | CODMOTIVO->CODMOTIVO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
