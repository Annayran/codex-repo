# TGAIQT — Isenção Quebra

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| IsencaoQuebra | Isenção Quebra | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Código | I |  |  | N |
| CODUSU | Código Usuário | I |  |  | N |
| DTAPUR | Data de Apuração | D |  |  | N |
| DTNEG | Data de Negociação | H |  |  | N |
| NUIQT | Número Único | I |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| OBS | Observação | S |  |  | N |
| QTDISENTAR | Qntd. a Isentar | F |  |  | N |
| QUEBRAPUR | Quebra Apurada | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| IsencaoQuebra / [TGAIQT](TGAIQT.md) | MotivoNegQuebra / [TGAMNQ](TGAMNQ.md) | CODIGO->CODIGO | I |
| IsencaoQuebra / [TGAIQT](TGAIQT.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
