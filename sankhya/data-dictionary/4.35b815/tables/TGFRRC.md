# TGFRRC — Registro retorno cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RegistroRetornoCartao | Registro retorno cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DATASOURCE | Fonte de dados | S |  |  | N |
| LARGTOTAL | Largura total | I |  |  | N |
| NUARC | Nro. único | I |  |  | N |
| OBRIGATORIO | Obrigatório | S |  |  | N |
| QUERYEXPORTACAO | Consulta de exportação | C |  |  | N |
| SEQREG | Sequência | I |  |  | N |
| SQLPARAMS | Parametros | C |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ArquivoRetornoCartao / [TGFARC](TGFARC.md) | RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | NUARC->NUARC | I |
| CampoRetornoCartao / [TGFCRC](TGFCRC.md) | RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | NUARC->NUARC; SEQREG->SEQREG | I |
| RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | ArquivoRetornoCartao / [TGFARC](TGFARC.md) | NUARC->NUARC | I |
| RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | CampoRetornoCartao / [TGFCRC](TGFCRC.md) | NUARC->NUARC; SEQREG->SEQREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
