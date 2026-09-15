# TGFMTC — TABLE TGFMTC

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotivoCancCotacao | Motivo de Cancelamento da Cotação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODIGO | Código | I |  |  | N |
| MOTIVO | Motivo de Cancelamento | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CabecalhoCotacao / [TGFCOT](TGFCOT.md) | MotivoCancCotacao / [TGFMTC](TGFMTC.md) | CODMOTCAN->CODIGO | I |
| ItemCotacao / [TGFITC](TGFITC.md) | MotivoCancCotacao / [TGFMTC](TGFMTC.md) | CODMOTCAN->CODIGO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
