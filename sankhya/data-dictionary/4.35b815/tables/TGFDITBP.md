# TGFDITBP — Importação tributos detalhes de IPI

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheImportacaoTributoIPI | Detalhe Integração Tributo IPI | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ALIQIPI | Alíquota IPI | F |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| CODENQ | Código do Enquadramento | S |  |  | N |
| CODPROD | Produto | I |  |  | N |
| CSTENT | CST Entrada | I |  |  | N |
| CSTSAI | CST Saída | I |  |  | N |
| DHIMPORT | Data | H |  |  | N |
| EX | Exceção | S |  |  | N |
| NCM | NCM | S |  |  | N |
| STATUSIMP | Status importação | S |  |  | N |
| TIPOIMP | Tipo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoIPI / [TGFDITBP](TGFDITBP.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| DetalheImportacaoTributoIPI / [TGFDITBP](TGFDITBP.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ImportacaoTributoIPI / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoIPI / [TGFDITBP](TGFDITBP.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
