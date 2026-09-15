# TRSANU — Anúncio de Vagas

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 12
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnuncioVagas | Anúncio de Vagas | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANUNCIO | Cód. Anúncio | I |  |  | N |
| DESCRVAGA | Descrição da Vaga | S |  |  | N |
| DTENTRADA | Data Abertura | D |  |  | N |
| DTTERMINO | Data Término | D |  |  | N |
| FAIXASALARIALFINAL | Faixa Salarial Final | F |  |  | N |
| FAIXASALARIALINICIAL | Faixa Salarial | F |  |  | N |
| GRAUINSTR | Grau de Instrução | I |  |  | N |
| NUREQUISICAO | Nro Requisição | I |  |  | N |
| NUVAGAS | Nro de Vagas | I |  |  | N |
| RESUMO | Resumo da Vaga | S |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnuncioVagas / [TRSANU](TRSANU.md) | PerfilVaga / [TRSPFV](TRSPFV.md) | CODANUNCIO->CODANUNCIO | I |
| AnuncioVagas / [TRSANU](TRSANU.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | AnuncioVagas / [TRSANU](TRSANU.md) | CODANUNCIO->CODANUNCIO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
