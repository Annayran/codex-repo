# TGFDITB — Integração Tributo Detalhe

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 4
- Relações DWF: 13
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DetalheImportacaoTributo | DetalheImportacaoTributo | S | S |
| ImportacaoTributoFederal | Integração Tributo Federal | S | S |
| ImportacaoTributoICMS | Integração Tributo ICMS | S | S |
| ImportacaoTributoIPI | Integração Tributo IPI | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Código da Empresa | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DHIMPORT | Data | H |  |  | N |
| STATUS | Status | S |  |  | N |
| TIPOIMP | Tipo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | ImportacaoTributoFederal / [TGFDITB](TGFDITB.md) | CODEMP->CODEMP | I |
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | ImportacaoTributoICMS / [TGFDITB](TGFDITB.md) | CODEMP->CODEMP | I |
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | ImportacaoTributoIPI / [TGFDITB](TGFDITB.md) | CODEMP->CODEMP | I |
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoFed / [TGFDITBF](TGFDITBF.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoICMS / [TGFDITBI](TGFDITBI.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoIPI / [TGFDITBP](TGFDITBP.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| DetalheImportacaoTributo / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoTributoFederal / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoFed / [TGFDITBF](TGFDITBF.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| ImportacaoTributoFederal / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoTributoICMS / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoICMS / [TGFDITBI](TGFDITBI.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| ImportacaoTributoICMS / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| ImportacaoTributoIPI / [TGFDITB](TGFDITB.md) | DetalheImportacaoTributoIPI / [TGFDITBP](TGFDITBP.md) | CODEMP->CODEMP; DHIMPORT->DHIMPORT; TIPOIMP->TIPOIMP | I |
| ImportacaoTributoIPI / [TGFDITB](TGFDITB.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
