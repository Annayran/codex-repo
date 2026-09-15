# TGMFCT — Previsão Meta

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 29
- Instâncias DWF: 1
- Relações DWF: 16
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ForecastMeta | Previsão Meta | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANO | Ano | I |  |  | N |
| CAMPODETALHE | Campo detalhe | S |  |  | N |
| CODCENARIO | Codigo cenário | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODGER | Gerente de Vendedor | I |  |  | N |
| CODGRUPONAT | Cód. Grupo Nat. | I |  |  | N |
| CODGRUPOPROD | Grupo Produto | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODMETA | Cód. Meta | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPAIS | Cód. País | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODREG | Cód. Região | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |
| CODUF | Cód. UF | I |  |  | N |
| CODUSU | Usuário Solicitante | I |  |  | N |
| CODVEND | CODVEND | I |  |  | S |
| CONTROLE | Controle | S |  |  | N |
| EXECUTANTE | Executante | I |  |  | S |
| MARCA | Marca | S |  |  | N |
| MES | Mês | I |  |  | N |
| NOMECENARIO | Nome do cenário | S |  |  | N |
| OBSERVACAO | Observação | S |  |  | N |
| VENDEDOR | Executante | I |  |  | S |
| VENDITEM | Executante | I |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ForecastMeta / [TGMFCT](TGMFCT.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
