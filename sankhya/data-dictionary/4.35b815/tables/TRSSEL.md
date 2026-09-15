# TRSSEL — Processo de Seleção

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProcessoSelecao | Processo de Seleção | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANUNCIO | Anúncio | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CODUSURESPONSAVEL | Responsável | I |  |  | N |
| DESCRSELECAO | Descrição | S |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| DTFECHAMENTO | Data de Fechamento | D |  |  | N |
| DTINICIO | Data de Início | D |  |  | N |
| DTPREVTERMINO | Previsão de Término | D |  |  | N |
| NUREQUISICAO | Requisição | I |  |  | N |
| NUSELECAO | Código | I |  |  | N |
| OBS | Observações | S |  |  | N |
| PERSONALIZAR | Personalizar | S |  |  | N |
| QTDVAGASPRE | Qtd. Vagas Preenchidas | I |  |  | N |
| QTDVAGASREQ | Qtd. Vagas Requisitadas | I |  |  | N |
| SITSELECAO | Situação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | AnuncioVagas / [TRSANU](TRSANU.md) | CODANUNCIO->CODANUNCIO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | Candidato / [TRSCAN](TRSCAN.md) | NUREQUISICAO->NUREQUISICAO; NUSELECAO->NUSELECAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | EtapaPersonalizada / [TRSEPE](TRSEPE.md) | NUSELECAO->NUSELECAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | StatusEtapas / [TRSSTE](TRSSTE.md) | NUSELECAO->NUSELECAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSURESPONSAVEL->CODUSU | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | ProcessoSelecao / [TRSSEL](TRSSEL.md) | NUSELECAO->NUSELECAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
