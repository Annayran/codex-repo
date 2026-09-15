# TGFRTT — Restrição de Tipo de Título

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| RestricaoTipoTitulo | Restrição para troca no caixa | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODTIPTITDES | Tipo de Título Proibido | I |  |  | N |
| CODTIPTITORI | Tipo de Título Origem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| RestricaoTipoTitulo / [TGFRTT](TGFRTT.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | CODTIPTITDES->CODTIPTIT | I |
| TipoTitulo / [TGFTIT](TGFTIT.md) | RestricaoTipoTitulo / [TGFRTT](TGFRTT.md) | CODTIPTIT->CODTIPTITORI | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
