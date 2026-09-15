# TCSPSC — Produtos e Serviços do Contrato

- Módulo técnico: [TCS](../modules/TCS.md)
- Campos: 23
- Instâncias DWF: 2
- Relações DWF: 21
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ProdutoServicoContrato | Produtos/Serviço do Contrato | S | N |
| ProdutoServicoContrato2 | Produtos/Serviço do Contrato | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARCPREF | Cód. Parceiro Fornecedor preferencial | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CODUSUALTREG | Cód. Usuário Alteração | I |  |  | N |
| DHALTREG | Dt. Alteração | H |  |  | N |
| DTVERSAO | Data da versão | D |  |  | N |
| FREQUENCIA | Frequência | I |  |  | N |
| GRUPIMPRESSAO | Grupo de impressão | I |  |  | N |
| IMPRNOTA | Imprimir nota | S |  |  | N |
| IMPROS | Imprimir OS | S |  |  | N |
| LIMITANTE | Limitante | S |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| NUMSERIE | Número de série | S |  |  | N |
| NUMUSUARIOS | Número de usuários | F |  |  | N |
| OBSERVACAO | Local de instalação | S |  |  | N |
| PRODPRINC | Produto principal | S |  |  | N |
| QTDEPREVISTA | Qtd. Prevista | F |  |  | N |
| QTDMESES | Quantidade de meses do contrato | I |  |  | N |
| QTDUSU | Quantidade de usuário / login | I |  |  | N |
| SERFATURCON | Série para faturamento em contratos | S |  |  | N |
| SITPROD | Situação | S |  |  | N |
| TOPFATURCON | TOP para faturamento em contratos | I |  |  | N |
| VERSAO | Versão | S |  |  | N |
| VLRUNIT | Valor unitário | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Bilhetagem / [TGFBIL](TGFBIL.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| Contrato / [TCSCON](TCSCON.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | NUMCONTRATO->NUMCONTRATO | I |
| ContratoArmazenagemGeral / [TCSCON](TCSCON.md) | ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | NUMCONTRATO->NUMCONTRATO | I |
| PrecoContrato / [TCSPRE](TCSPRE.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| PrecoPorFaixa / [TCSPPF](TCSPPF.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | OcorrenciaContrato / [TCSOCC](TCSOCC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PendenciaFaturamentoProRata / [TCSPFP](TCSPFP.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PrecoContrato / [TCSPRE](TCSPRE.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PrecoDescontoFaixaQtd / [TCSPREQTD](TCSPREQTD.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | PrecoPorFaixa / [TCSPPF](TCSPPF.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | TipoOperacaoGrupoFaturamento / [TGFTOP](TGFTOP.md) | TOPFATURCON->CODTIPOPER | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | UltimoPrecoContrato / [TCSPRE](TCSPRE.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | VendedorProdutoContrato / [TCSVPC](TCSVPC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | Contrato / [TCSCON](TCSCON.md) | NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | PrecoContrato / [TCSPRE](TCSPRE.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |
| ProdutoServicoContrato2 / [TCSPSC](TCSPSC.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ProdutoServicoSerie / [TCSPSS](TCSPSS.md) | ProdutoServicoContrato / [TCSPSC](TCSPSC.md) | CODPROD->CODPROD; NUMCONTRATO->NUMCONTRATO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
