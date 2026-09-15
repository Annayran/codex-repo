# TIMPRF — Profissões e Ramos de Atividade

- Módulo técnico: [TIM](../modules/TIM.md)
- Campos: 4
- Instâncias DWF: 3
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| TimProfissaoAtividadeGrupo | Grupo. Prof. / Ramos de Atividade | S | N |
| TimProfissaoConjuge | Profissão do Conjuge | S | N |
| TimProfissaoRamoAtividade | Prof. / Ramos de Atividade | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| PRFAGRUPADOR | Agrupador | I |  |  | N |
| PRFCODIGO | Código | I |  |  | N |
| PRFPROFISSAO | Profissão | S |  |  | N |
| PRFTIPO | Tipo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Contato / [TGFCTT](TGFCTT.md) | TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | TIMPROFISSAO->PRFCODIGO | I |
| Parceiro / [TGFPAR](TGFPAR.md) | TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | TIMPROFISSAO->PRFCODIGO | I |
| TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | TimProfissaoAtividadeGrupo / [TIMPRF](TIMPRF.md) | PRFAGRUPADOR->PRFCODIGO | I |
| TimProspect / [TIMPPR](TIMPPR.md) | TimProfissaoConjuge / [TIMPRF](TIMPRF.md) | PPRPROFCONJUGE->PRFCODIGO | I |
| TimProspect / [TIMPPR](TIMPPR.md) | TimProfissaoRamoAtividade / [TIMPRF](TIMPRF.md) | PPRPROFISSAO->PRFCODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
