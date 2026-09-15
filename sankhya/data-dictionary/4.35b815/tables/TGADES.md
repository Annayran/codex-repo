# TGADES — Desconto contratos armazenagem

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 10
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ApuracaoArmazenagemDesc | Desc Apur. Cont. Armarzenagem | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDES | Cod Desconto | I |  |  | N |
| CODTRA | Expedição | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DATACAD | Data | H |  |  | N |
| DATAFINAL | Data Final | H |  |  | N |
| DATAINICIO | Data Inicio | H |  |  | N |
| MOVDESC | Motivo | S |  |  | N |
| NUMCONTRATO | Número do contrato | I |  |  | N |
| TIPO | Tipo | S |  |  | N |
| VLRDES | Valor | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ApuracaoArmazenagemDesc / [TGADES](TGADES.md) | ApuracaoArmazenagemTrans / [TGATRA](TGATRA.md) | CODTRA->CODTRA | I |
| ApuracaoArmazenagemDesc / [TGADES](TGADES.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
