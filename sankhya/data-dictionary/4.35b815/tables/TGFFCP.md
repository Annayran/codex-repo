# TGFFCP — Fórmula de Composição do Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 20
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaComposicaoProduto | Fórmula de Composição do Produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CICLOPRODUCAO | Ciclo de produção | F |  |  | N |
| CODEST | Estrutura de Produção | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPRC | Cód. Processo | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DESVIO | Desvio Sup.(%) | F |  |  | N |
| DESVIOINF | Desvio Inf.(%) | F |  |  | N |
| FORMPRINCIPAL | Fórmula principal | S |  |  | N |
| IDPROC | IDPROC | I |  |  | N |
| MARGLUCRO | Margem de lucro | F |  |  | N |
| MENORDTVAL | Considerar Menor Dt. Validade ? | S |  |  | N |
| MULTIPLOIDEAL | Múltiplo Ideal | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| PRODUCAOMIN | Produção Mínima | F |  |  | N |
| REGMAPA | Registrada no M.A.P.A? | S |  |  | N |
| UNIDCICLO | Unidade do Ciclo | S |  |  | N |
| VARIACAO | Variação | I |  |  | N |
| VARIACAOREL | Variação Relacionada | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProduto / [TGFEPR](TGFEPR.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | EstruturaProducao / [TGFESP](TGFESP.md) | CODEST->CODEST | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | EtapaProduto / [TGFEPR](TGFEPR.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | Tamanholoteformula / [TGFFTL](TGFFTL.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| PlanejamentoProducao / [TGFPPL](TGFPPL.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| Tamanholoteformula / [TGFFTL](TGFFTL.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
