# TGFICP — Ítens de Composição de Produto

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 48
- Instâncias DWF: 1
- Relações DWF: 11
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ItemComposicaoProduto | Componentes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATUALESTOQUE | Atualiza Estoque | S |  |  | N |
| CALCATIVO | Ativo | S |  |  | S |
| CALCCODVOLPRO | Unid.Cadastro | S |  |  | S |
| CALCCUSGER | Custo Gerencial | F |  |  | S |
| CALCCUSGERUNID | Cst.Ger.Unid.Cadastro | F |  |  | S |
| CALCCUSMED | Custo Médio | F |  |  | S |
| CALCCUSMEDUNID | Cst.Méd.Ger.Unid.Cadastro | F |  |  | S |
| CALCCUSREP | Custo Reposição | F |  |  | S |
| CALCCUSREPUNID | Cst.Rep.Unid.Cadastro | F |  |  | S |
| CALCCUSVAR | Custo Variável | F |  |  | S |
| CALCCUSVARUNID | Cst.Var.Unid.Cadastro | F |  |  | S |
| CALCMARCA | Marca | S |  |  | S |
| CALCPERCCALCCUSVAR | % Custo Variável | F |  |  | S |
| CALCPERCCUSREP | % Custo Reposição | F |  |  | S |
| CALCPERCUSCGER | % Custo Gerencial | F |  |  | S |
| CALCPERCVLRVENDAFIXO | % Valor Venda Fixo | F |  |  | S |
| CALCREFERENCIA | Referência | S |  |  | S |
| CALCVLRVENDAFIXO | Valor Venda Fixo | F |  |  | S |
| CALCVLRVENDAFIXOUNID | Valor Venda Fixo Unid.Cadastro | F |  |  | S |
| CODETAPA | Etapa de produção do produto | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODLOCALMP | Local | I |  |  | N |
| CODMATPRIMA | Matéria Prima | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODVOL | Unid. | S |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| CONTROLEMP | Controle | S |  |  | N |
| DESVIOPADRAO | Desvio Padrão | F |  |  | N |
| FIXO | Quantidade fixa | S |  |  | N |
| MANTEMQTD | Mantém quantidade | S |  |  | N |
| MPDECQTD | Decimais para qtd | I |  |  | S |
| NIVEISRECUO | Níveis recuo | I |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| ONDEEXEC | Executa em | S |  |  | N |
| OPCIONAL | Opcional | S |  |  | N |
| PODECONCLUIR | Pode concluir | S |  |  | N |
| PRECO | Preço | F |  |  | N |
| QTDMISTURA | Qtd. Mistura | F |  |  | N |
| REFERENCIAMP | Referência (MP) | S |  |  | S |
| SEGUEANTERIOR | Segue anterior | S |  |  | N |
| SELECIONAPROX | Seleciona próximo | S |  |  | N |
| SEQUENCIA | Sequência | I |  |  | N |
| SINCRFLUXO | Junção fluxo | S |  |  | N |
| TERCEIROS | Terceiros | S |  |  | N |
| TIPTRANSICAO | Tipo de transição | S |  |  | N |
| TRANSICAO | MP de transição? | S |  |  | N |
| VARIACAO | Variação | I |  |  | N |
| VARIARCONTROLE | Permite variação de controle | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EtapaProduto / [TGFEPR](TGFEPR.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODETAPA->CODETAPA; CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | FormulaComposicaoProduto / [TGFFCP](TGFFCP.md) | CODLOCAL->CODLOCAL; CODPROD->CODPROD; CONTROLE->CONTROLE; VARIACAO->VARIACAO | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | LocalMateriaPrima / [TGFLOC](TGFLOC.md) | CODLOCALMP->CODLOCAL | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | ProdutoMateriaPrima /  | CODMATPRIMA->CODPROD | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | Servico / [TGFPRO](TGFPRO.md) | CODMATPRIMA->CODPROD | I |
| ItemComposicaoProduto / [TGFICP](TGFICP.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |
| Produto / [TGFPRO](TGFPRO.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODPROD->CODPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | ItemComposicaoProduto / [TGFICP](TGFICP.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
