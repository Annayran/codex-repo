# TGFARC — Arquivo retorno cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 11
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: T

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ArquivoRetornoCartao | Arquivo retorno cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CARACTERESEP | Separador campos | S |  |  | N |
| CHRTERMREG | Terminador registro | S |  |  | N |
| CODUSU | Usuário | I |  |  | N |
| DATASOURCE | Fonte de dados | S |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |
| DHALTER | Dt. alteração | H |  |  | N |
| NUARC | Nro. único | I |  |  | N |
| QUERYEXPORTACAO | Consulta de exportação | C |  |  | N |
| SQLPARAMS | Parametros | C |  |  | N |
| TIPARQ | Tipo arquivo | S |  |  | N |
| TIPOTERMREG | Tipo terminador reg. | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ArquivoRetornoCartao / [TGFARC](TGFARC.md) | RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | NUARC->NUARC | I |
| ArquivoRetornoCartao / [TGFARC](TGFARC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | ArquivoRetornoCartao / [TGFARC](TGFARC.md) | NUARC->NUARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
