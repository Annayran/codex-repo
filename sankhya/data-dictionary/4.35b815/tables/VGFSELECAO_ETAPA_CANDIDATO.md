# VGFSELECAO_ETAPA_CANDIDATO — VGFSELECAO_ETAPA_CANDIDATO

- Módulo técnico: [VGF](../modules/VGF.md)
- Campos: 15
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ViewSelecaoEtapaCandidato | Seleção Etapa Candidato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODETAPA | Cód. Etapa Padrão | I |  |  | N |
| CODSTAETAPA | Cód. Status Etapa | I |  |  | N |
| CODUSU | Cód. Responsável | I |  |  | N |
| DESCRETAPA | Descr. Etapa | S |  |  | N |
| DESCRSELECAO | Descr. Seleção | S |  |  | N |
| DHFINAL | Dh. Final | H |  |  | N |
| DHINICIO | Dh. Início | H |  |  | N |
| NOMECANDIDATO | Nome Completo | S |  |  | N |
| NUCURRICULO | Nro. Currículo | I |  |  | N |
| NUEVENTO | Nro. Evento | I |  |  | N |
| NUREQUISICAO | Nro. Requisição | I |  |  | N |
| NUSELECAO | Cód. Seleção | I |  |  | N |
| RESULTADO | Nota | F |  |  | N |
| STATUSETAPA | Status por Etapa | S |  |  | N |
| TIPOETAPA | Tipo Etapa Selecionada | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ViewSelecaoEtapaCandidato / [VGFSELECAO_ETAPA_CANDIDATO](VGFSELECAO_ETAPA_CANDIDATO.md) | Executante / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
