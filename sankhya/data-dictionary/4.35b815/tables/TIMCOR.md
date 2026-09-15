# TIMCOR — Corretores

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 36
- Instâncias DWF: 8
- Relações DWF: 33
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimCaptador | Corretor Captador | S | N |
| TimCaptadorImovel | Captadores dos imóveis | S | N |
| TimCorretor | Corretor | S | N |
| TimCorretor2 | Corretores | S | N |
| TimCorretorEquipe | Equipe do Gerente | S | N |
| TimCorretorGerente | Corretor Gerente | S | N |
| TimCorretorGerente2 | Corretor Gerente | S | N |
| TimGerenteCorretor | Gerente de corretores | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CORADMINISTRATIVO | Administrativo | S |  |  | N |
| CORCAPTADOR | Captador | S |  |  | N |
| CORCENTROCUSTO | Centro de Resultado | I |  |  | N |
| CORCODIGO | Código | I |  |  | N |
| CORCORRETOR | Corretor | S |  |  | N |
| CORCRECI | CRECI | I |  |  | N |
| CORDATAADMISSAO | Dt. Admissão | D |  |  | N |
| CORDATADESLIGAMENTO | Dt. Desligamento | D |  |  | N |
| CORDTINCLUSAO | Dt. Inclusão | D |  |  | N |
| CORDTINICIOCONT | Dt. Início Contrato | D |  |  | N |
| CORECOLABORADOR | Colaborador | S |  |  | N |
| COREGERENTE | Gerente | S |  |  | N |
| COREMAILCARTAOVISITA | E-mail Cartão | S |  |  | N |
| COREPAINDICVENDA | Indicador de venda | S |  |  | N |
| COREPAUNIDGER | Unidade gerencial | I |  |  | N |
| COREPAVLRMETAGERENTE | Vlr. da meta do gerente | S |  |  | N |
| COREPAVLRMETAIND | Vlr. da meta individual | S |  |  | N |
| CORESTAGIARIO | Estagiário | S |  |  | N |
| CORESTAGIO | Ativo | S |  |  | N |
| COREXPIRACRECI | Dt. Expiração | D |  |  | N |
| COREXTERNO | Externo | S |  |  | N |
| CORFIMCONTRATO | Dt. Fim Contrato | D |  |  | N |
| CORGERENTE | Superior Imediato | I |  |  | N |
| CORIMOBILIARIA | Imobiliária | I |  |  | N |
| CORINDICADOR | Indicador | S |  |  | N |
| CORLOCACAO | Locação | S |  |  | N |
| CORNOMECARTAOVISITA | Nome Cartão | S |  |  | N |
| CORPARCEIRO | Parceiro | I |  |  | N |
| CORPASTA | Nro. Pasta Física | I |  |  | N |
| CORPERIODO | Período Atuação | S |  |  | N |
| CORPROCESSO | Nro. Processo | I |  |  | N |
| CORREGIAO | Região | I |  |  | N |
| CORSUPERVISOR | Supervisor | S |  |  | N |
| CORUSUARIO | Usuário Inclusão | I |  |  | N |
| CORUSUARIOALT | Usuário Alteração | I |  |  | N |
| CORVENDA | Venda | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| TimAtendimentoCliente / [TIMSAC](TIMSAC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | SACCORRETOR->CORCODIGO | I |
| TimClassificadoAnuncio / [TIMCFI](TIMCFI.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CLAGERENTECOR->CORCODIGO | I |
| TimContratoAdm / [TIMADM](TIMADM.md) | TimCorretor / [TIMCOR](TIMCOR.md) | ADMCAPTADOR->CORCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CLACORRETOR->CORCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | CLAGERENTECOR->CORCODIGO | I |
| TimContratoVenda / [TIMCLA](TIMCLA.md) | TimCorretorGerente2 / [TIMCOR](TIMCOR.md) | CLAGERGERCOR->CORCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | LTVCORRETOR->CORCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimCorretorGerente2 / [TIMCOR](TIMCOR.md) | LTVSUPERVISORCOR->CORCODIGO | I |
| TimContratoVendaLote / [TIMLTV](TIMLTV.md) | TimGerenteCorretor / [TIMCOR](TIMCOR.md) | LTVGERENTECOR->CORCODIGO | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | CentroResultado / [TSICUS](TSICUS.md) | CORCENTROCUSTO->CODCENCUS | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CORPARCEIRO->CODPARC | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimCorretorEquipe / [TIMCOR](TIMCOR.md) | CORCODIGO->CORGERENTE | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | CORGERENTE->CORCODIGO | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimImobiliaria / [TIMIMB](TIMIMB.md) | CORIMOBILIARIA->IMBCODIGO | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimUsuarioAlteracao / [TSIUSU](TSIUSU.md) | CORUSUARIOALT->CODUSU | I |
| TimCorretor / [TIMCOR](TIMCOR.md) | TimUsuarioInclusao / [TSIUSU](TSIUSU.md) | CORUSUARIO->CODUSU | I |
| TimCorretorEquipe / [TIMCOR](TIMCOR.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CORGERENTE->CORCODIGO | I |
| TimEmpreendimento / [TIMEPR](TIMEPR.md) | TimCorretor / [TIMCOR](TIMCOR.md) | EPRGERENTEEMP->CORCODIGO | I |
| TimFichaAtendimentoCliente / [TIMFAC](TIMFAC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | FACCORRETOR->CORCODIGO | I |
| TimImobiliaria / [TIMIMB](TIMIMB.md) | TimCorretor / [TIMCOR](TIMCOR.md) | IMBGERENTE->CORCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | IMVCAPTADORVENDA->CORCODIGO | I |
| TimImovel / [TIMIMV](TIMIMV.md) | TimCorretor2 / [TIMCOR](TIMCOR.md) | IMVCAPTADORLOC->CORCODIGO | I |
| TimImovelChaves / [TIMCHV](TIMCHV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CORCODIGO->CORCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | LOCCORRETOR->CORCODIGO | I |
| TimLocacao / [TIMLOC](TIMLOC.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | LOCATENDENTE->CORCODIGO | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | TimCorretor / [TIMCOR](TIMCOR.md) | LOTGERENTE->CORCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimCorretor / [TIMCOR](TIMCOR.md) | PPVCORRETOR->CORCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimCorretorGerente / [TIMCOR](TIMCOR.md) | PPVCORGERENTE->CORCODIGO | I |
| TimPropostaVenda / [TIMPPV](TIMPPV.md) | TimCorretorGerente2 / [TIMCOR](TIMCOR.md) | PPVCORSUPERVISOR->CORCODIGO | I |
| TimRateioComisaoVenda / [TIMRCL](TIMRCL.md) | TimCorretor / [TIMCOR](TIMCOR.md) | RCLCORRETOR->CORCODIGO | I |
| TimViewFichaAtendimentoCliente / [VIMFAC](VIMFAC.md) | TimCorretor / [TIMCOR](TIMCOR.md) | FACCORRETOR->CORCODIGO | I |
| TimVinculoDocumento / [TIMVDO](TIMVDO.md) | TimCorretor / [TIMCOR](TIMCOR.md) | VDOCORRETOR->CORCODIGO | I |
| Usuario / [TSIUSU](TSIUSU.md) | TimCorretor / [TIMCOR](TIMCOR.md) | CORCODIGO->CORCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
