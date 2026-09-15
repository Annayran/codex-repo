# TWFPRN — Processos de Negócios

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProcessoNegocio | Processos de Negócios | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Publicado | S |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| CODUSUALTER | Usuário resp. alteração | I |  |  | N |
| CODUSUDONO | Usuário resp. cadastro | I |  |  | N |
| DHALTER | Dh. alteração | H |  |  | N |
| DHCRIACAO | Dh. cadastro | H |  |  | N |
| DHPUBLICACAO | Dh. publicação | H |  |  | N |
| DOCUMENTACAO | Documentação | C |  |  | N |
| IMGBPMN | Imagem do BPMN | C |  |  | N |
| NOME | Descrição | S |  |  | N |
| NUGRUPO | Grupo Processo | I |  |  | N |
| ULTIMAVERSAO | Ultima versão | S |  |  | S |
| VERANTDHPUBLICACAO | Dh. publicação da versão anterior | H |  |  | S |
| VERSAO | Versão | I |  |  | N |
| XMLBPMN | XML do BPMN | C |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ElementoProcesso / [TWFELE](TWFELE.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |
| EventoProcessoNegocio / [TWFEVE](TWFEVE.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |
| InstanciaProcesso / [TWFIPRN](TWFIPRN.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | CompartilhamentoProcesso / [TWFCPN](TWFCPN.md) | CODPRN->CODPRN | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | GestorProcesso / [TWFGPR](TWFGPR.md) | CODPRN->CODPRN | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | GrupoProcesso / [TWFGRU](TWFGRU.md) | NUGRUPO->NUGRUPO | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSUDONO->CODUSU | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | Usuario2 / [TSIUSU](TSIUSU.md) | CODUSUALTER->CODUSU | I |
| ProcessoNegocio / [TWFPRN](TWFPRN.md) | UsuarioSubstitutoTarefa / [TWFUST](TWFUST.md) | CODPRN->CODPRN | I |
| SLAProcessoNegocio / [TWFRSLA](TWFRSLA.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |
| ViewInstanciaTarefa / [VWFITAR](VWFITAR.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | PROCESSID->CODPRN; PROCESSVERSION->VERSAO | I |
| ViewListaTarefa / [VWFTAR](VWFTAR.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | COD_PROCESSO->CODPRN; VERSAO_PROCESSO->VERSAO | I |
| ViewSolicitacao / [VWFSOLI](VWFSOLI.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | COD_PROCESSO->CODPRN; VERSAO->VERSAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
