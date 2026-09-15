# TGFCITB — Cabecalho Imp. Tribut.

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CabecalhoImportacaoTributo | Cabecalho Importacao Tributo | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVA | Ativo | S |  |  | N |
| CODEMP | Código da Empresa | I |  |  | N |
| FILTROCARAC | Filtro Característica | S |  |  | N |
| FILTROCFOP | Filtro CFOP | S |  |  | N |
| FILTROPROD | Filtro Produto | S |  |  | N |
| FILTROUF | Filtro UF | S |  |  | N |
| FILTROUSOPROD | Filtro Uso do produto | S |  |  | N |
| LOGIN | Login | S |  |  | N |
| SENHA | Senha | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | ImportacaoTributoFederal / [TGFDITB](TGFDITB.md) | CODEMP->CODEMP | I |
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | ImportacaoTributoICMS / [TGFDITB](TGFDITB.md) | CODEMP->CODEMP | I |
| CabecalhoImportacaoTributo / [TGFCITB](TGFCITB.md) | ImportacaoTributoIPI / [TGFDITB](TGFDITB.md) | CODEMP->CODEMP | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
