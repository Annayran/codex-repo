# TSIUFS — Unidade Federativa

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 30
- Instâncias DWF: 4
- Relações DWF: 30
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| UnidadeFederativa | Unidade Federativa | S | S |
| UnidadeFederativaDestino | Unidade Federativa Destino | S | N |
| UnidadeFederativaEmissao | Unidade Federativa Emissão | S | N |
| UnidadeFederativaOrigem | Unidade Federativa Origem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCAMPOEXTRAGNRE | Código (Campos Extras GNRE) | I |  |  | N |
| CODCAMPOEXTRAGNRE2 | Código (Campos Extras GNRE 2) | I |  |  | N |
| CODCLSVENCDIME | Código Classe Vencimento (DIME) | I |  |  | N |
| CODCLSVENCDIMEFCPST | Código Classe Vencimento (DIME) p/ FCP ST | I |  |  | N |
| CODDETGNRE | Código Detalhamento Receita (GNRE) | I |  |  | N |
| CODFCPSTGNRE | Código da Receita (GNRE) p/ FCP ST | I |  |  | N |
| CODIBGE | Código IBGE | I |  |  | N |
| CODPAIS | País | I |  |  | N |
| CODPARCSECRECEST | Cód. Parceiro Secretaria da Receita Estadual | I |  |  | N |
| CODPRODGNRE | Código do Produto (GNRE) | I |  |  | N |
| CODRECDIME | Código da Receita (DIME) | I |  |  | N |
| CODRECDIMEFCPST | Código da Receita (DIME) para FCP ST | I |  |  | N |
| CODSTGNRE | Código da Receita (GNRE) | S |  |  | N |
| CODUF | Cód. Unidade Federativa | I |  |  | N |
| CODUSUALTREG | Cód. Usuário Alteração | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTREG | Dt. Alteração | H |  |  | N |
| ESTORNONFE | Permite Estorno de NF-e | S |  |  | N |
| GERINFCAMPADICGNRE | Gera Informação dos Campos Adicionais na GNRE | S |  |  | N |
| PROTOCOLOCONVENIO | Protocolo/Convênio (GNRE) | S |  |  | N |
| TABPRECOPF | Tabela de Preço PF | I |  |  | N |
| TABPRECPMC | Tabela de Preço PMC | I |  |  | N |
| TIPODOC | Tipo de documento | I |  |  | N |
| TIPOINFO | Tipo da Informação | S |  |  | N |
| TIPTITGNREFCPST | Tipo de Título p/ indicar GNRE p/ FCP S.T. | I |  |  | N |
| UF | Sigla | S |  |  | N |
| USAWEBSERVICEGNRE | Utiliza webservice para GNRE | S |  |  | N |
| VALORCAMPOEXTRA | Valor para Campo Extra GNRE | S |  |  | N |
| VERSAOGNRE | Versão GNRE | S |  |  | N |
| VLRPERSGNRE | Valor personalizado GNRE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AliquotaICMS / [TGFICM](TGFICM.md) | UnidadeFederativaDestino / [TSIUFS](TSIUFS.md) | UFDEST->CODUF | I |
| AliquotaICMS / [TGFICM](TGFICM.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | UFORIG->CODUF | I |
| AliquotaInternaDestino / [TGFAID](TGFAID.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| AlteracaoMetaOrcamento / [TGMAPO](TGMAPO.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFVEICULO->CODUF | I |
| CabecalhoNota / [TGFCAB](TGFCAB.md) | UnidadeFederativaEmissao / [TSIUFS](TSIUFS.md) | UFEMISS->CODUF | I |
| Cidade / [TSICID](TSICID.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UF->CODUF | I |
| ControlePortaria / [TGARPO](TGARPO.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| Curriculo / [TFCBCO](TFCBCO.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFCPS->CODUF | I |
| DeclaracaoImportacao / [TGFIDI](TGFIDI.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUFDESEMB->CODUF | I |
| EventoMDF / [TGFEMDF](TGFEMDF.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFEVE->CODUF | I |
| Feriado / [TSIFER](TSIFER.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| ForecastMeta / [TGMFCT](TGMFCT.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| InscricaoUnidadeFederativa / [TSIIUF](TSIIUF.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | UFFINAL->CODUF | I |
| Manifesto / [TGFMDFE](TGFMDFE.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | UFINICIAL->CODUF | I |
| MetaAtual / [TGMMET](TGMMET.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| ParceiroProspect / [TCSPAP](TCSPAP.md) | UnidadeFederativaDestino / [TSIUFS](TSIUFS.md) | UFRG->CODUF | I |
| PesagemMultipla / [TGAPES](TGAPES.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | UnidadeFederativaDestino / [TSIUFS](TSIUFS.md) | UFDEST->CODUF | I |
| RotaCalculoFrete / [TGFRCF](TGFRCF.md) | UnidadeFederativaOrigem / [TSIUFS](TSIUFS.md) | UFORIG->CODUF | I |
| Transferencia / [TGMTRA](TGMTRA.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| UFsPercurso / [TGFUFP](TGFUFP.md) | UnidadeFederativa / [TSIUFS](TSIUFS.md) | CODUF->CODUF | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | GNREUnidadeFederativa / [TSIGUF](TSIGUF.md) | CODUF->CODUF | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | Pais / [TSIPAI](TSIPAI.md) | CODPAIS->CODPAIS | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARCSECRECEST->CODPARC | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | TIPTITGNREFCPST->CODTIPTIT | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | UnidadesFiscaisPorUF / [TSIUFU](TSIUFU.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
