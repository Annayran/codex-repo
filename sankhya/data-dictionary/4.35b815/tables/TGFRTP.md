# TGFRTP — Parceiros da Rota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParceirosRota | Parceiros da Rota | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODROTA | Rota | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | ParceirosRota / [TGFRTP](TGFRTP.md) | CODPARC->CODPARC | I |
| ParceirosRota / [TGFRTP](TGFRTP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceirosRota / [TGFRTP](TGFRTP.md) | Rota / [TGFROT](TGFROT.md) | CODROTA->CODROTA | I |
| ParceirosRota / [TGFRTP](TGFRTP.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |
| Rota / [TGFROT](TGFROT.md) | ParceirosRota / [TGFRTP](TGFRTP.md) | CODROTA->CODROTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
