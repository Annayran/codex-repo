# TGFCKC — Cabeçalho do Checkout

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 10
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CheckoutCabecalho | Cabeçalho do Checkout | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCENCUS | Centro Resultado | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODNAT | Natureza | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| CODTIPOPER | Tipo Operação | I |  |  | N |
| CODTIPVENDA | Tipo Negociação | I |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| CODVEND | Vendedor | I |  |  | N |
| CPFCNPJADQUIRENTE | Cpf/Cnpj Adquirente | S |  |  | N |
| NUCKC | Nro. único | I |  |  | N |
| NULAYOUT | Layout | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | CentroResultado / [TSICUS](TSICUS.md) | CODCENCUS->CODCENCUS | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | LayoutNota / [TGFLAY](TGFLAY.md) | NULAYOUT->NULAYOUT | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Natureza / [TGFNAT](TGFNAT.md) | CODNAT->CODNAT | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | TipoNegociacao / [TGFTPV](TGFTPV.md) | CODTIPVENDA->CODTIPVENDA | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | TipoOperacao / [TGFTOP](TGFTOP.md) | CODTIPOPER->CODTIPOPER | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| CheckoutCabecalho / [TGFCKC](TGFCKC.md) | Vendedor / [TGFVEN](TGFVEN.md) | CODVEND->CODVEND | I |
| CheckoutItem / [TGFCKI](TGFCKI.md) | CheckoutCabecalho / [TGFCKC](TGFCKC.md) | NUCKC->NUCKC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
