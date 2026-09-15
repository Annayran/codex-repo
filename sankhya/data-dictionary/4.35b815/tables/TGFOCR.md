# TGFOCR — Ocorrência de Caixa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 13
- Instâncias DWF: 1
- Relações DWF: 6
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrenciaGC | Ocorrência de Caixa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODORIGEM | Cód. Origem | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DESCRICAO | Descrição Ocorrência | S |  |  | S |
| DTOCORRENCIA | Dt. Ocorrência | H |  |  | N |
| GERATITULO | Gera Título Financeiro? | S |  |  | N |
| HISTOCORRENCIA | Historico da Ocorrência | S |  |  | N |
| MOTIVOFIN | Motivo de não gerar o título Financeiro | S |  |  | N |
| NUCONF | Número da Conferencia | I |  |  | N |
| NUFIN | Financeiro | I |  |  | N |
| ORIGEM | Origem | S |  |  | N |
| VALOR | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | OcorrenciaGC / [TGFOCR](TGFOCR.md) | CODORIGEM->CODORIGEM | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | ConferenciaFechamentoCaixa / [TGFCCAI](TGFCCAI.md) | NUCONF->NUCONF | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Financeiro / [TGFFIN](TGFFIN.md) | NUFIN->NUFIN | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| OcorrenciaGC / [TGFOCR](TGFOCR.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
