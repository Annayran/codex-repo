# TGFGRU — Grupos de Produtos

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 40
- Instâncias DWF: 3
- Relações DWF: 34
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoProduto | Grupo Produto | S | S |
| GrupoProduto2 | Grupo Produto | S | N |
| GrupoProduto3 | GrupoProduto3 | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| AGRUPALOCVALEST | Agrupar local na validação de estoque | S |  |  | N |
| ANALITICO | Analítico | S |  |  | N |
| APRPRODVDA | Apresenta produtos desse grupo na venda consultiva? | S |  |  | N |
| AREAOCUPUNID | Área ocupada por unidade | I |  |  | N |
| ATIVO | Ativo | S |  |  | N |
| CALRUPTURAESTOQUE | Calcula Ruptura de Estoque? | S |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODGRUPAI | Grupo pai | I |  |  | N |
| CODGRUPOPROD | Cód. do Grupo Produto | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODRFA | Regra armazenagem | I |  |  | N |
| CODUSU | Cód. Usuário Alteração | I |  |  | N |
| COMCURVA_A | Comissão curva A | F |  |  | N |
| COMCURVA_B | Comissão curva B | F |  |  | N |
| COMCURVA_C | Comissão curva C | F |  |  | N |
| CONSGRUPRODCAT42 | Considerar grupo de produto/serviço na geração da Cat 42? | S |  |  | N |
| DESCRGRUPOPROD | Descrição | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| GRAU | Grau | I |  |  | N |
| GRUPOICMS | Grupo de ICMS | I |  |  | N |
| IMAGEM | Imagem | B |  |  | N |
| LIMCURVA_B | Limite Curva B | F |  |  | N |
| LIMCURVA_C | Limite Curva C | F |  |  | N |
| METAQTD | Meta quantitativa | F |  |  | N |
| PARTICMETA | Participação na meta | F |  |  | N |
| PEDIRLIB | Pedir confirmação de liberação | S |  |  | N |
| PERCCMTEST | % Carga Média Trib. Estadual | F |  |  | N |
| PERCCMTFED | % Carga Média Trib. Federal | F |  |  | N |
| PERCCMTIMP | % Carga Média Trib. Importação | F |  |  | N |
| PERCCMTMUN | % Carga Média Trib. Municipal | F |  |  | N |
| PERCCMTNAC | % Carga Média Trib. Nacional | F |  |  | N |
| PERCMETA | Meta percentual | F |  |  | N |
| PERCMETACONTRIB | Meta contribuição | F |  |  | N |
| QTDEXPOSICAO | Quantidade em exposição | F |  |  | N |
| SOLCOMPRA | Solicita Compra | S |  |  | N |
| TIPOIMPOSTO | Tipo de Imposto | S |  |  | S |
| VALEST | Valida estoque | S |  |  | N |
| VISIVELAPPOS | Apresentar produtos no aplicativo de Ordem de Serviço | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaImposto / [TGFIMA](TGFIMA.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODIGO->CODGRUPOPROD | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ComissaoVendedorGrupo / [TGFCVG](TGFCVG.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| DescontoFob / [TGFDFO](TGFDFO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| FichaAcompanhamentoDiario / [TGFFAD](TGFFAD.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GiroProduto / [TGFGIR](TGFGIR.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoPlanejamentoPlantio / [TGAGPL](TGAGPL.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | AliquotaImposto / [TGFIMA](TGFIMA.md) | CODGRUPOPROD->CODIGO; TIPOIMPOSTO->TIPO | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | L |
| GrupoProduto / [TGFGRU](TGFGRU.md) | Produto / [TGFPRO](TGFPRO.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | RegraArmazenagem /  | CODRFA->CODRFA | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | VendaCasada / [TGFVCS](TGFVCS.md) | CODGRUPOPROD->CODORIG | I |
| GrupoProduto / [TGFGRU](TGFGRU.md) | VendedorGrupoProduto / [TGFRGV](TGFRGV.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODINSTPRINC->CODGRUPOPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | GrupoProduto2 / [TGFGRU](TGFGRU.md) | CODINSTSECINI->CODGRUPOPROD | I |
| ItemRegras / [TGFITR](TGFITR.md) | GrupoProduto3 / [TGFGRU](TGFGRU.md) | CODINSTSECFIN->CODGRUPOPROD | I |
| MetaAtual / [TGMMET](TGMMET.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| MovimentoMensalSintetico / [TGFMMS](TGFMMS.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| PadraoClassificacao / [TGACLT](TGACLT.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Produto / [TGFPRO](TGFPRO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ProdutoContato / [TGFCTTPRO](TGFCTTPRO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ProdutosPreAcordoVerba / [TGFGVR](TGFGVR.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| RegraSLA / [TCSRSL](TCSRSL.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Servico / [TGFPRO](TGFPRO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| TaxasJurosDescontos / [TGFTAXG](TGFTAXG.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| Transferencia / [TGMTRA](TGMTRA.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| VendedorGrupoProduto / [TGFRGV](TGFRGV.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
