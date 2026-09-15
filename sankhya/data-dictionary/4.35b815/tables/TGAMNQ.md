# TGAMNQ — Motivos de Negociações de Quebras

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotivoNegQuebra | Motivos de Negociações de Quebras | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Código | I |  |  | N |
| DESCRICAO | Descrição | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| IsencaoQuebra / [TGAIQT](TGAIQT.md) | MotivoNegQuebra / [TGAMNQ](TGAMNQ.md) | CODIGO->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
