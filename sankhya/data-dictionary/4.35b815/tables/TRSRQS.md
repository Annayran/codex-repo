# TRSRQS — Requisição seleção Curriculo

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 2
- Instâncias DWF: 2
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RequisicaoSelecaoCurriculo | Requisição de seleção do Currículo | N | N |
| SelecaoRequisicao | Seleção Requisição | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUREQUISICAO | NUREQUISICAO | I |  |  | N |
| NUSELECAO | NUSELECAO | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| SelecaoRequisicao / [TRSRQS](TRSRQS.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
