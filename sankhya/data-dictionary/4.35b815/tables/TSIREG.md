# TSIREG — Regiões

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 31
- Instâncias DWF: 3
- Relações DWF: 23
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Regiao | Região | S | S |
| Regiao2 | Região | S | N |
| RegiaoTabPreco | Região Tabela de Preço | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICA | Analítica | S |  |  | N |
| ATIVA | Ativa | S |  |  | N |
| CODREG | Cód.Região | I |  |  | N |
| CODREGPAI | Cód.Região Pai | I |  |  | N |
| CODTAB | Tabela Preço | I |  |  | N |
| CODTABMIN | Tabela Preço Mínimo | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| FRETEMIN | Frete mínimo | F |  |  | N |
| GERARRECEITA | Tipo | S |  |  | N |
| GRAU | Grau | I |  |  | N |
| NOMEREG | Nome | S |  |  | N |
| PERCADICIONAL | Perc. Adicional | F |  |  | N |
| PERCCUSVAR | % Custo Variável | F |  |  | N |
| PERCDESCFOB | % Desc. FOB | F |  |  | N |
| PERCPREMIO | Perc. Prêmio | F |  |  | N |
| SEMANA1_DOM | % Domingo | F |  |  | N |
| SEMANA1_QUA | % Quarta | F |  |  | N |
| SEMANA1_QUI | % Quinta | F |  |  | N |
| SEMANA1_SAB | % Sábado | F |  |  | N |
| SEMANA1_SEG | % Segunda | F |  |  | N |
| SEMANA1_SEX | % Sexta | F |  |  | N |
| SEMANA1_TER | % Terça | F |  |  | N |
| SEMANA2_DOM | % Domingo | F |  |  | N |
| SEMANA2_QUA | % Quarta | F |  |  | N |
| SEMANA2_QUI | % Quinta | F |  |  | N |
| SEMANA2_SAB | % Sábado | F |  |  | N |
| SEMANA2_SEG | % Segunda | F |  |  | N |
| SEMANA2_SEX | % Sexta | F |  |  | N |
| SEMANA2_TER | % Terça | F |  |  | N |
| VENDAMIN | Venda Mínima | F |  |  | N |
| VLRANTECIPACAO | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Bairro / [TSIBAI](TSIBAI.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| CabecalhoPlanejamentoEntrega / [TGFPLAC](TGFPLAC.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Cidade / [TSICID](TSICID.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Contato / [TGFCTT](TGFCTT.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| ControleEntrega / [TGFVIS](TGFVIS.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| MetaAtual / [TGMMET](TGMMET.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| OrdemCarga / [TGFORD](TGFORD.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Parceiro / [TGFPAR](TGFPAR.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Regiao / [TSIREG](TSIREG.md) | Bairro / [TSIBAI](TSIBAI.md) | CODREG->CODREG | I |
| Regiao / [TSIREG](TSIREG.md) | Cidade / [TSICID](TSICID.md) | CODREG->CODREG | I |
| Regiao / [TSIREG](TSIREG.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Regiao / [TSIREG](TSIREG.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTABMIN->CODTAB | I |
| Regiao / [TSIREG](TSIREG.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODREG->CODREG | I |
| Regiao / [TSIREG](TSIREG.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| Regiao / [TSIREG](TSIREG.md) | VendedorParceiro / [TGFRPV](TGFRPV.md) | CODREG->CODREG | I |
| RegiaoTabPreco / [TSIREG](TSIREG.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| TimLoteamento / [TIMLOT](TIMLOT.md) | Regiao / [TSIREG](TSIREG.md) | LOTREGIAO->CODREG | I |
| Transferencia / [TGMTRA](TGMTRA.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| Vendedor / [TGFVEN](TGFVEN.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| VendedorParceiro / [TGFRPV](TGFRPV.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
