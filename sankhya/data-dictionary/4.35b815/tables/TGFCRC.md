# TGFCRC — Campo retorno cartão

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CampoRetornoCartao | Campo retorno cartão | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CAMPO | Campo | S |  |  | N |
| INICIO | Início | I |  |  | N |
| LARGURA | Largura | I |  |  | N |
| MASCARA | Máscara | S |  |  | N |
| NUARC | Nro. único | I |  |  | N |
| SEQCAMPO | Seq. campo | I |  |  | N |
| SEQREG | Sequência registro | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VALORIDENT | Valor literal | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CampoRetornoCartao / [TGFCRC](TGFCRC.md) | RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | NUARC->NUARC; SEQREG->SEQREG | I |
| RegistroRetornoCartao / [TGFRRC](TGFRRC.md) | CampoRetornoCartao / [TGFCRC](TGFCRC.md) | NUARC->NUARC; SEQREG->SEQREG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
