# TGMAPO — Alteração Planejamento Orçamentária

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 39
- Instâncias DWF: 1
- Relações DWF: 18
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AlteracaoMetaOrcamento | Alteração Planejamento Orçamentária | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANO | Ano | I |  |  | N |
| CODCENCUS | Cód.Centro Resultado | I |  |  | N |
| CODCID | Cód. Cidade | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODGER | Gerente de Vendedor | I |  |  | N |
| CODGRUPONAT | Cód. Grupo Nat. | I |  |  | N |
| CODGRUPOPROD | Grupo Produto | I |  |  | N |
| CODLOCAL | Local | I |  |  | N |
| CODMETA | Código | I |  |  | N |
| CODNAT | Cód. Natureza | I |  |  | N |
| CODPAIS | Cód. País | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODPROJ | Projeto | I |  |  | N |
| CODREG | Cód. Região | I |  |  | N |
| CODSOLICITANTE | Código solicitante | I |  |  | N |
| CODTIPPARC | Perfil | I |  |  | N |
| CODUF | Cód. UF | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHINTUNICO | Ano | D |  |  | N |
| EXECUTANTE | EXECUTANTE | I |  |  | S |
| MARCA | Marca | S |  |  | N |
| NUSOLICITACAO | Número solicitação | I |  |  | N |
| PREVISTO1 | Previsto 1 | F |  |  | N |
| PREVISTO10 | Previsto 10 | F |  |  | N |
| PREVISTO11 | Previsto 11 | F |  |  | N |
| PREVISTO12 | Previsto 12 | F |  |  | N |
| PREVISTO2 | Previsto 2 | F |  |  | N |
| PREVISTO3 | Previsto 3 | F |  |  | N |
| PREVISTO4 | Previsto 4 | F |  |  | N |
| PREVISTO5 | Previsto 5 | F |  |  | N |
| PREVISTO6 | Previsto 6 | F |  |  | N |
| PREVISTO7 | Previsto 7 | F |  |  | N |
| PREVISTO8 | Previsto 8 | F |  |  | N |
| PREVISTO9 | Previsto 9 | F |  |  | N |
| RECDESP | Receita/Despesa | S |  |  | N |
| VENDEDOR | VENDEDOR | I |  |  | S |
| VENDITEM | VENDITEM | I |  |  | S |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | SolicitacaoLiberacaoOrcamento / [TGMSLO](TGMSLO.md) | CODMETA->CODMETA; CODSOLICITANTE->CODSOLICITANTE; NUSOLICITACAO->NUSOLICITACAO | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Usuario / [TSIUSU](TSIUSU.md) | CODSOLICITANTE->CODUSU | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
