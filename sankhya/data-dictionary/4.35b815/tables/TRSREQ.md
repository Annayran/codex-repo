# TRSREQ — Requisição de Pessoal

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 35
- Instâncias DWF: 1
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RequisicaoPessoal | Requisição de Pessoal | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCARGAHOR | Horário de trabalho | I |  |  | N |
| CODCARGO | Cargo | I |  |  | N |
| CODDEP | Departamento | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODMOTIVO | Motivo | I |  |  | N |
| CODMOTIVOAVAL | Motivo Aval | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODUSU | Recrutador Responsável | I |  |  | N |
| CODUSUAVAL | Avaliador | I |  |  | N |
| CODUSUREQUISITANTE | Requisitante | I |  |  | N |
| DESCRREQUISICAO | Descrição Requisição | S |  |  | N |
| DHAVALIACAO | Dt. Avaliação | H |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| DTPREVATEND | Dt. Previsão Atend. | D |  |  | N |
| DTREQUISICAO | Data Requisição | D |  |  | N |
| FIMFAIXSAL | Fim faixa salarial | F |  |  | N |
| GRAUINSTR | Grau de Instrução | I |  |  | N |
| INIFAIXSAL | Início faixa salarial | F |  |  | N |
| JUSTIFICATIVA | Justificativa | S |  |  | N |
| NUREQUISICAO | Código | I |  |  | N |
| OBS | Detalhamento adicional | S |  |  | N |
| OBSAVALIACAO | Observações | S |  |  | N |
| OBSCARGO | Detalhamento cargo | S |  |  | S |
| PERMITEEXFUNC | Permite Ex. Funcionário | S |  |  | N |
| PRIORIDADE | Prioridade | I |  |  | N |
| QTDVAGAS | Qtd. Vagas | I |  |  | N |
| QTDVAGASATEND | Vagas Atendidas | I |  |  | N |
| SITREQUISICAO | Situação | I |  |  | N |
| STATUS | Status | S |  |  | N |
| STATUSAVAL | Status Avaliação | S |  |  | N |
| STATUSSELECAO | Status Seleção | I |  |  | S |
| TIPO | Tipo | I |  |  | N |
| TIPOSELECAO | Tipo Seleção | I |  |  | N |
| TIPVAGA | Tipo de Vaga | S |  |  | N |
| VINCULO | Vínculo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnuncioVagas / [TRSANU](TRSANU.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| EtapaPersonalizada / [TRSEPE](TRSEPE.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| PerfilRequisicao / [TRSPER](TRSPER.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | AvaliacaoRequisicao / [TRSAVR](TRSAVR.md) | NUREQUISICAO->NUREQUISICAO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Cargo /  | CODCARGO->CODCARGO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Departamento /  | CODDEP->CODDEP | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | IdentificacaoCargaHoraria /  | CODCARGAHOR->CODCARGAHOR | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | MotivoRequisicao / [TRSMOT](TRSMOT.md) | CODMOTIVO->CODMOTIVO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | PerfilCargo /  | CODCARGO->CODCARGO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | PerfilCargoSintetico /  | CODCARGO->CODCARGO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | PerfilRequisicao / [TRSPER](TRSPER.md) | NUREQUISICAO->NUREQUISICAO | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Responsavel / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RequisicaoPessoal / [TRSREQ](TRSREQ.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUREQUISITANTE->CODUSU | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| SelecaoRequisicao / [TRSRQS](TRSRQS.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
