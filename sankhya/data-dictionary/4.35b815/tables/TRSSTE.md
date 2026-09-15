# TRSSTE — Status Etapas

- Módulo técnico: [TRS](../modules/TRS.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| StatusEtapas | Status Etapas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODETAPA | Cód. Etapa | I |  |  | N |
| CODSTAETAPA | Cód. Status Etapa | I |  |  | N |
| DESCRETAPA | Descr. Etapa | S |  |  | S |
| DESCRNUCURRICULO | Nome Candidato | S |  |  | S |
| DESCRREQUISICAO | Descr. Requisição | S |  |  | S |
| NUCURRICULO | N° Curriculo | I |  |  | N |
| NUREQUISICAO | N° Requisição | I |  |  | N |
| NUSELECAO | N° Seleção | I |  |  | N |
| STATUSETAPA | Status do Candidato(Etapa) | S |  |  | N |
| TIPOETAPA | Tipo da Etapa | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapasProcessoSelecao / [TRSETA](TRSETA.md) | StatusEtapas / [TRSSTE](TRSSTE.md) | CODETAPA->CODETAPA | I |
| ProcessoSelecao / [TRSSEL](TRSSEL.md) | StatusEtapas / [TRSSTE](TRSSTE.md) | NUSELECAO->NUSELECAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
