# TRSPON — TRSPON

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PontuacaoEtapaCurriculo | Pontuação | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODETAPA | Cód.Etapa | I |  |  | N |
| CODUSUEXEC | Cód. Usuário Execução | I |  |  | N |
| CODUSUPLAN | Cód Usuário Planejamento | I |  |  | N |
| DTEXECUCAO | Data Execução | H |  |  | N |
| DTPLAN | Data Planejamento | H |  |  | N |
| NUCURRICULO | Número Curriculo | I |  |  | N |
| NUREQUISICAO | Número Requisição | I |  |  | N |
| NUSELECAO | Número Seleção | I |  |  | N |
| OBSEXEC | Obs Execução | S |  |  | N |
| OBSPLAN | Obs Planejamento | S |  |  | N |
| PESO | Peso | I |  |  | N |
| PONTUACAOMINIMA | Pontuação Mínima (%) | I |  |  | N |
| RESULTADO | Nota Etapa | F |  |  | N |
| SEQETAPA | Sequência | I |  |  | N |
| VALORETAPA | Valor Etapa | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| PontuacaoEtapaCurriculo / [TRSPON](TRSPON.md) | EtapaCurriculo / [TRSETA](TRSETA.md) | CODETAPA->CODETAPA | I |
| ViewProcessoSelecaoCurriculo / [VGFSELECAO_CURRICULO](VGFSELECAO_CURRICULO.md) | PontuacaoEtapaCurriculo / [TRSPON](TRSPON.md) | NUCURRICULO->NUCURRICULO; NUSELECAO->NUSELECAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
