# TRSCAN — Seleção Curriculo

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 14
- Instâncias DWF: 2
- Relações DWF: 12
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Candidato | Candidato | S | N |
| SelecaoCurriculo | Processos de Seleção que Participou | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa Pessoal | I |  |  | N |
| CODFUNC | Cód. funcionário | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| CODUSUAPROV | Cód. Avaliador | I |  |  | N |
| DTALTER | Data de Alteração | D |  |  | N |
| DTAPROV | Data avaliação | D |  |  | N |
| NOTASELECAO | Nota de seleção | F |  |  | N |
| NUCURRICULO | Número Curriculo | I |  |  | N |
| NUREQUISICAO | Cód. Requisição | I |  |  | N |
| NUSELECAO | Número de seleção | I |  |  | N |
| OBSAPROV | Observações do avaliador para a requisição | S |  |  | N |
| RESULTADO | Média obtida | F |  |  | N |
| STATUS | Situação | S |  |  | N |
| STATUSAPROV | Avaliação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Candidato / [TRSCAN](TRSCAN.md) | Curriculo / [TFCBCO](TFCBCO.md) | NUCURRICULO->NUCURRICULO | I |
| Candidato / [TRSCAN](TRSCAN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUAPROV->CODUSU | I |
| Candidato / [TRSCAN](TRSCAN.md) | ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | NUCURRICULO->NUCURRICULO | I |
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | Candidato / [TRSCAN](TRSCAN.md) | NUCURRICULO->NUCURRICULO; NUREQUISICAO->NUREQUISICAO; NUSELECAO->NUSELECAO | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | Candidato / [TRSCAN](TRSCAN.md) | NUREQUISICAO->NUREQUISICAO; NUSELECAO->NUSELECAO | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | Curriculo / [TFCBCO](TFCBCO.md) | NUCURRICULO->NUCURRICULO | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | EmpresaPessoal /  | CODEMP->CODEMP | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | Funcionario /  | CODFUNC->CODFUNC | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | ProcessoSelecao / [TRSSEL](TRSSEL.md) | NUSELECAO->NUSELECAO | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | RequisicaoPessoal / [TRSREQ](TRSREQ.md) | NUREQUISICAO->NUREQUISICAO | I |
| SelecaoCurriculo / [TRSCAN](TRSCAN.md) | UsuarioAvaliador / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | SelecaoCurriculo / [TRSCAN](TRSCAN.md) | NUCURRICULO->NUCURRICULO; NUSELECAO->NUSELECAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
