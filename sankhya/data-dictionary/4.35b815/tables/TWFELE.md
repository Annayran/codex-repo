# TWFELE — Elementos de Processo

- Módulo técnico: [TWF](../modules/TWF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ElementoProcesso | Elementos de Processo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BACKGROUND | Background | S |  |  | N |
| CODPRN | Cód. Processo | I |  |  | N |
| DOCUMENTACAO | Documentação | C |  |  | N |
| EXPRESSCANDITADE | Expressão candidato | S |  |  | N |
| EXPRESSLANGUAGE | Expressão linguagem | S |  |  | N |
| HISTORYTEMPLATE | Template de histórico | C |  |  | N |
| IDELEMENTO | Id. Elemento | S |  |  | N |
| NOME | Nome | S |  |  | N |
| NOTIFICATIONCANDIDATE | Notificação ao Candidato | S |  |  | N |
| NOTIFICATIONOWNER | Notificação ao Dono | S |  |  | N |
| NUELE | Núm. Elemento | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VERSAO | Versão | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ElementoProcesso / [TWFELE](TWFELE.md) | ProcessoNegocio / [TWFPRN](TWFPRN.md) | CODPRN->CODPRN; VERSAO->VERSAO | I |
| FormularioProcessoNegocio / [TWFFORM](TWFFORM.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |
| PrazoVencimentoTarefa / [TWFPVT](TWFPVT.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |
| PropriedadeElementoProcesso / [TWFPRE](TWFPRE.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |
| VariavelProcesso / [TWFVAR](TWFVAR.md) | ElementoProcesso / [TWFELE](TWFELE.md) | NUELE->NUELE | I |
| ViewInstanciaTarefa / [VWFITAR](VWFITAR.md) | ElementoProcesso / [TWFELE](TWFELE.md) | PROCESSID->CODPRN; PROCESSVERSION->VERSAO; TASKIDELEMENTO->IDELEMENTO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
