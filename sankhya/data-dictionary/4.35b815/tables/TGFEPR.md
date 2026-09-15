# TGFEPR — Etapas do Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 7
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EtapaProduto | Etapas do Produto | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CICLOPRODUCAO | Ciclo de Produção | F |  |  | N |
| CODETAPA | Etapa | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| FINAL | Final | S |  |  | N |
| OBRIGATORIA | Obrigatória | S |  |  | N |
| ONDEEXEC | Executa em | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| UNIDCICLO | Unidade do Ciclo de Produção | S |  |  | N |
| VARIACAO | Variação | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProducao / [TGFETA](TGFETA.md) | EtapaProduto / [TGFEPR](TGFEPR.md) | CODETAPA->CODETAPA | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | EtapaProducao / [TGFETA](TGFETA.md) | CODETAPA->CODETAPA | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODETAPA->CODETAPA; CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| EtapaProduto / [TGFEPR](TGFEPR.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | EtapaProduto / [TGFEPR](TGFEPR.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
