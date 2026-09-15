# TGFNTA — Nome das Tabelas de Preço

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 10
- Instâncias DWF: 7
- Relações DWF: 31
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NomeTabelaPrecoFilha2 | Nome das Tabelas de Preço | N | N |
| NomeTabelasPreco | Cadastro de Tabela de Preço | S | N |
| NomeTabelasPrecoFarPop | Nome das Tabelas de Preço | S | N |
| NomeTabelasPrecoFilha | Nome das Tabelas de Preço | S | N |
| NomeTabelasPrecoOrigem | Tabela de Preço de Origem | N | N |
| NomeTabelasPrecoPmpf | Nome das Tabelas de Preço | S | N |
| NomeTabelasUFDestino | Nome das Tabelas de UF Destino | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODMOEDA | Moeda | I |  |  | N |
| CODREG | Região | I |  |  | N |
| CODTAB | Código | I |  |  | N |
| CODTABFLEX | Tabela p/ Flex | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |
| DECVENDA | Casas decimais | I |  |  | N |
| INTEGRAECONECT | Integrar com EConect | S |  |  | N |
| NOMETAB | Nome | S |  |  | N |
| OBS | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaICMS / [TGFICM](TGFICM.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| AliquotaICMS / [TGFICM](TGFICM.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTABICMS->CODTAB | I |
| AliquotaPisCofinsCssl / [TGFIFE](TGFIFE.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| CentroResultado / [TSICUS](TSICUS.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| ComplementoParc / [TGFCPL](TGFCPL.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTABCOT->CODTAB | I |
| Desconto / [TGFDES](TGFDES.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelaPrecoFilha2 / [TGFNTA](TGFNTA.md) | CODTABALT->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTABCALC->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | NomeTabelasPrecoOrigem / [TGFNTA](TGFNTA.md) | CODTABCKC->CODTAB | I |
| LocalFinanceiro / [TGFLOC](TGFLOC.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | Moeda / [TSIMOE](TSIMOE.md) | CODMOEDA->CODMOEDA | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTABFLEX->CODTAB | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| NomeTabelasPreco / [TGFNTA](TGFNTA.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Parceiro / [TGFPAR](TGFPAR.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTABST->CODTAB | I |
| ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| PrecificacaoPorTop / [TGFPPT](TGFPPT.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Produto / [TGFPRO](TGFPRO.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Regiao / [TSIREG](TSIREG.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| Regiao / [TSIREG](TSIREG.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTABMIN->CODTAB | I |
| TabelaPreco / [TGFTAB](TGFTAB.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| TabelaPreco / [TGFTAB](TGFTAB.md) | NomeTabelasPrecoOrigem / [TGFNTA](TGFNTA.md) | CODTABORIG->CODTAB | I |
| TabelaPrecoCR / [TGFCRT](TGFCRT.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| TipoAtendimento / [TGFTPA](TGFTPA.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| TipoNegociacao / [TGFTPV](TGFTPV.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| VendedorVenda / [TGFNPV](TGFNPV.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| VendedorVenda / [TGFNPV](TGFNPV.md) | NomeTabelasPrecoFilha / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
