# TSIGUF — GNRE Unidade Federativa

- Módulo técnico: [TSI](../modules/TSI.md)
- Campos: 21
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GNREUnidadeFederativa | GNRE Unidade Federativa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCAMPOEXTRAGNRE | Código (Campos Extras GNRE) | I |  |  | N |
| CODCAMPOEXTRAGNRE2 | Código (Campos Extras GNRE 2) | I |  |  | N |
| CODCLSVENCDIME | Código Classe Vencimento (DIME) | I |  |  | N |
| CODCLSVENCDIMEFCPST | Código Classe Vencimento (DIME) p/ FCP ST | I |  |  | N |
| CODDETGNRE | Código Detalhamento Receita (GNRE) | I |  |  | N |
| CODFCPSTGNRE | Código da Receita (GNRE) p/ FCP ST | I |  |  | N |
| CODGUF | Código GNRE Unidade Federativa | I |  |  | N |
| CODOBR | Código | I |  |  | N |
| CODPRODGNRE | Código do Produto (GNRE) | I |  |  | N |
| CODRECDIME | Código da Receita (DIME) | I |  |  | N |
| CODRECDIMEFCPST | Código da Receita (DIME) para FCP ST | I |  |  | N |
| CODSTGNRE | Código da Receita (GNRE) | S |  |  | N |
| CODUF | Cód. Unidade Federativa | I |  |  | N |
| GERINFCAMPADICGNRE | Gera Informação dos Campos Adicionais na GNRE | S |  |  | N |
| PROTOCOLOCONVENIO | Protocolo/Convênio (GNRE) | S |  |  | N |
| TIPAPURACAO | Tipo Apuração | S |  |  | N |
| TIPODOC | Tipo de documento | I |  |  | N |
| TIPOINFO | Tipo da Informação | S |  |  | N |
| VALORCAMPOEXTRA | Valor para Campo Extra GNRE | S |  |  | N |
| VERSAOGNRE | Versão GNRE | S |  |  | N |
| VLRPERSGNRE | Valor personalizado GNRE | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormulaParcelasIndependentes / [TGFFPI](TGFFPI.md) | GNREUnidadeFederativa / [TSIGUF](TSIGUF.md) | CODGUF->CODGUF | I |
| UnidadeFederativa / [TSIUFS](TSIUFS.md) | GNREUnidadeFederativa / [TSIGUF](TSIGUF.md) | CODUF->CODUF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
