# TGMMET — Meta

- Módulo técnico: [TGM](../modules/TGM.md)
- Campos: 41
- Instâncias DWF: 1
- Relações DWF: 19
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MetaAtual | Meta Atual | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Analítico | S |  |  | N |
| ANTECIPDESP | Antecipação | F |  |  | N |
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
| CODVEND | Vendedor | I |  |  | N |
| CODVOL | Unidade | S |  |  | N |
| COMPROMISSODESP | Compromisso | F |  |  | N |
| CONTROLE | Controle | S |  |  | N |
| DHALTER | Dt. Alteração | H |  |  | N |
| DIA | Dia | I |  |  | N |
| DTREF | Período | D |  |  | N |
| MARCA | Marca | S |  |  | N |
| PERCAVISO | % Aviso | F |  |  | N |
| PERCENTUAL | Percentual | F |  |  | N |
| PREVDESP | Despesa Prevista | F |  |  | N |
| PREVREC | Receita Prevista | F |  |  | N |
| QTDPREV | Qtde.Prevista | F |  |  | N |
| QTDREAL | Qtde.Real | F |  |  | N |
| REALDESP | Despesa Real | F |  |  | N |
| REALREC | Receita Real | F |  |  | N |
| REDUCAODESP | Redução | F |  |  | N |
| SEMANAMES | Semana do mês | I |  |  | N |
| SUPLEMENTODESP | Suplemento | F |  |  | N |
| TIPOMSG | Tipo | S |  |  | N |
| TOTALAUTINV | Total Autorização de Investimento | F |  |  | N |
| TRANSFDESP | Transferência | F |  |  | N |
| TRANSFSALDODESP | Transf.Saldo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | MetaAtual / [TGMMET](TGMMET.md) | CODMETA->CODMETA | I |
| MetaAtual / [TGMMET](TGMMET.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| MetaAtual / [TGMMET](TGMMET.md) | Cidade / [TSICID](TSICID.md) | CODCID->CODCID | I |
| MetaAtual / [TGMMET](TGMMET.md) | ConfiguracaoMeta / [TGMCFG](TGMCFG.md) | CODMETA->CODMETA | I |
| MetaAtual / [TGMMET](TGMMET.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| MetaAtual / [TGMMET](TGMMET.md) | Gerente / [TGFVEN](TGFVEN.md) | CODGER->CODVEND | I |
| MetaAtual / [TGMMET](TGMMET.md) | GrupoNaturezas / [TGFGNT](TGFGNT.md) | CODGRUPONAT->CODGRUPONAT | I |
| MetaAtual / [TGMMET](TGMMET.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| MetaAtual / [TGMMET](TGMMET.md) | LocalFinanceiro / [TGFLOC](TGFLOC.md) | CODLOCAL->CODLOCAL | I |
| MetaAtual / [TGMMET](TGMMET.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| MetaAtual / [TGMMET](TGMMET.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| MetaAtual / [TGMMET](TGMMET.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MetaAtual / [TGMMET](TGMMET.md) | Perfil / [TGFTPP](TGFTPP.md) | CODTIPPARC->CODTIPPARC | I |
| MetaAtual / [TGMMET](TGMMET.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| MetaAtual / [TGMMET](TGMMET.md) | Projeto / [TCSPRJ](TCSPRJ.md) | CODPROJ->CODPROJ | I |
| MetaAtual / [TGMMET](TGMMET.md) | Regiao / [TSIREG](TSIREG.md) | CODREG->CODREG | I |
| MetaAtual / [TGMMET](TGMMET.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| MetaAtual / [TGMMET](TGMMET.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| MetaAtual / [TGMMET](TGMMET.md) | Volume / [TGFVOL](TGFVOL.md) | CODVOL->CODVOL | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
