# TGFROT — Rota

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 3
- Relações DWF: 8
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Rota | Rota | S | N |
| RotaAnterior | Rota Anterior | S | N |
| RotaPosterior | Rota Posterior | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ATIVO | Ativo | S |  |  | N |
| CODROTA | Código da rota | I |  |  | N |
| CODROTAANTERIOR | Cód. Rota Anterior | I |  |  | N |
| CODROTAPOSTERIOR | Cód. Rota Posterior | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DESCRROTA | Descrição | S |  |  | N |
| DISTANCIA | Distância (Km) | I |  |  | N |
| DTALTER | Data/Hora | H |  |  | N |
| OBS | Observação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| OrdemCarga / [TGFORD](TGFORD.md) | Rota / [TGFROT](TGFROT.md) | CODROTA->CODROTA | I |
| ParceirosRota / [TGFRTP](TGFRTP.md) | Rota / [TGFROT](TGFROT.md) | CODROTA->CODROTA | I |
| Rota / [TGFROT](TGFROT.md) | CategoriasRota / [TGFROTCAT](TGFROTCAT.md) | CODROTA->CODROTA | I |
| Rota / [TGFROT](TGFROT.md) | ParceirosRota / [TGFRTP](TGFRTP.md) | CODROTA->CODROTA | I |
| Rota / [TGFROT](TGFROT.md) | RotaAnterior / [TGFROT](TGFROT.md) | CODROTAANTERIOR->CODROTA | I |
| Rota / [TGFROT](TGFROT.md) | RotaPosterior / [TGFROT](TGFROT.md) | CODROTAPOSTERIOR->CODROTA | I |
| Rota / [TGFROT](TGFROT.md) | SequenciaEntrega / [TGFRCD](TGFRCD.md) | CODROTA->CODROTA | I |
| SequenciaEntrega / [TGFRCD](TGFRCD.md) | Rota / [TGFROT](TGFROT.md) | CODROTA->CODROTA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
