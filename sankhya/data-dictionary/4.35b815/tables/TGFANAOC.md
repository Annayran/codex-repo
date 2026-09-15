# TGFANAOC — Analise de Ocorrência

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 17
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AnaliseOcorrencia | Analise de Ocorrencia de Gestão de Caixa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANALISE | Cód. Analise de Ocorrência | I |  |  | N |
| CODEMP | Cód. Empresa | I |  |  | N |
| CODORIGEM | Cód. Ocorrência | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPDV | Cód. PDV | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHMOV | Dt. Geração Movimentação Financeira | D |  |  | N |
| DTOCORRENCIA | Dt. Ocorrência | D |  |  | N |
| FINGERADO | Financeiro Gerado | S |  |  | N |
| GERATITULO | Gera Título Financeiro? | S |  |  | N |
| HISTOCORRENCIA | Historico da Ocorrência | S |  |  | N |
| MOTIVOFIN | Motivo de não gerar o título | S |  |  | N |
| NUCONF | Número da Conferência | I |  |  | N |
| NUFIN | Nro. Único Movimentação Financeira | I |  |  | N |
| ORIGEM | Receita/Despesa | S |  |  | N |
| RAZAOSOCIAL | Razão Social | S |  |  | N |
| VALOR | Valor Ocorrência | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | CadastroPDV / [TGFPDV](TGFPDV.md) | CODPDV->CODPDV | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | OcorrenciaGC / [TGFOCR](TGFOCR.md) | CODORIGEM->CODORIGEM | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AnaliseOcorrencia / [TGFANAOC](TGFANAOC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
