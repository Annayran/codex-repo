# TGFGIR1 — Consolidação de Giro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 25
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConsolidacaoGiro | Consolidação do Giro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Empresa | I |  |  | N |
| CODLOCALORIG | Local Origem | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODTIPOPER | Top | I |  |  | N |
| CODVOL | Volume | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| CURVAQTD | Curva (Qtd) | S |  |  | N |
| CURVAQTDMED | Curva Qtd Média | S |  |  | N |
| CURVATOT | Curva Total | S |  |  | N |
| CURVATOTMED | Curva Total Média | S |  |  | N |
| CUSGER | Custo Gerencial | F |  |  | N |
| CUSMEDICM | Custo Médio (Com ICMS) | F |  |  | N |
| CUSREP | Custo de Reposição | F |  |  | N |
| CUSSEMICM | Custo (Sem ICMS) | F |  |  | N |
| CUSTOVARIAVEL | Custo Variável | F |  |  | N |
| CUSVARIAVEL | Custo Variável | F |  |  | N |
| DTNEG | Data da Negociação | D |  |  | N |
| ENTRADACOMICMS | Entrada (com ICMS) | F |  |  | N |
| ENTRADASEMICMS | Entrada (sem ICMS) | F |  |  | N |
| LUCRO | Lucro | F |  |  | N |
| MARGEMCONTRIB | Margem de Contribuição | F |  |  | N |
| POPULARIDADE | Popularidade | I |  |  | N |
| QTDNEG | Qtd. Negociada | F |  |  | N |
| VLRTOT | Valor Total | F |  |  | N |
| VLRUNIT | Valor Unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | EmpresaProdutoImpostos / [TGFPEM](TGFPEM.md) | CODEMP->CODEMP; CODPROD->CODPROD | I |
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ConsolidacaoGiro / [TGFGIR1](TGFGIR1.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
