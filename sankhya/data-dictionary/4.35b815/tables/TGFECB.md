# TGFECB — Eventos de cobrança

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EventosCobranca | Eventos de cobrança | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEVENTO | Número evento | I |  |  | N |
| CODREGUA | Régua | I |  |  | N |
| CONFIG | CONFIG | C |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Data Alteração | H |  |  | N |
| DIAEXEC | Dia execução | I |  |  | N |
| EXECAPOS | Executa após | I |  |  | N |
| FILTRO | Filtro | C |  |  | N |
| TIPO | Tipo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ExecReguaCobranca / [TGFEXR](TGFEXR.md) | EventosCobranca / [TGFECB](TGFECB.md) | CODEVENTO->CODEVENTO; CODREGUA->CODREGUA; TIPOEVENTO->TIPO | I |
| ReguaCobranca / [TGFRCB](TGFRCB.md) | EventosCobranca / [TGFECB](TGFECB.md) | CODREGUA->CODREGUA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
