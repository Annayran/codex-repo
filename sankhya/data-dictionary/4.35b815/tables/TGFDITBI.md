# TGFDITBI — Importação tributos detalhes de ICMS

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 27
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheImportacaoTributoICMS | Detalhe Integração Tributo ICMS | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQICMSINTERESTADUAL | Alíquota de ICMS Interestadual | F |  |  | N |
| ALIQICMSINTERNA | Alíquota de ICMS | F |  |  | N |
| ALIQICMSST | Alíquota do ICMS ST | F |  |  | N |
| ANTECIPADO | Antecipação | S |  |  | N |
| CARACTRIB | Característica tributável | S |  |  | N |
| CFOP | CFOP | I |  |  | N |
| CODBENEF | Código do benefício fiscal | S |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| CODESPECST | CEST | I |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CSOSN | CSOSN | I |  |  | N |
| CST | CST | I |  |  | N |
| DESONERADO | Desoneração | S |  |  | N |
| DHIMPORT | Data | H |  |  | N |
| FCP | Alíquota de ICMS FCP do produto | F |  |  | N |
| FINALIDADE | Destinação da mercadoria adquirida | S |  |  | N |
| IDALIQICMS | Código Alíquota ICMS | I |  |  | N |
| ISENTO | Isento | S |  |  | N |
| IVA | MVA/IVA do produto | F |  |  | N |
| MVAAJUSTADO | MVA/IVA Ajustada | F |  |  | N |
| NCM | NCM | S |  |  | N |
| PDIFER | Percentual do Diferimento de ICMS | F |  |  | N |
| REDUCAOBCICMS | Redução da Base de Cálculo do ICMS | F |  |  | N |
| REDUCAOSTICMS | Redução da Base de Cálculo do ICMS ST | F |  |  | N |
| STATUSIMP | Status importação | S |  |  | N |
| TIPOIMP | Tipo | I |  |  | N |
| UF | UF | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoICMS / [TGFDITBI](TGFDITBI.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| DetalheImportacaoTributoICMS / [TGFDITBI](TGFDITBI.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ImportacaoTributoICMS / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoICMS / [TGFDITBI](TGFDITBI.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
