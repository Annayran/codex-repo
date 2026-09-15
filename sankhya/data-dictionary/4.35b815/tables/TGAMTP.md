# TGAMTP — Tabela de Motivo de Paradas de Equipamentos

- Módulo técnico: [TGA](../modules/TGA.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotivoParadasEquipamentos | Tabela de Motivo de Paradas de Equipamentos | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ANALITICO | Indica de o Motivo de Parada é Analitico ou Sintético | S |  |  | N |
| ATIVO | Indica de o Motivo de Parada esta ativado no sistema | S |  |  | N |
| CODMOT | Cod. Motivo de Parada | I |  |  | N |
| CODMOTPAI | Cod. Pai do Motivo de Parada na Hierarquia | I |  |  | N |
| DESCRMOT | Desc. Motivo de Parada | S |  |  | N |
| GRAU | Nível de Hierarquia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AgendamentoParadaEquipamentos / [TGAPAR](TGAPAR.md) | MotivoParadasEquipamentos / [TGAMTP](TGAMTP.md) | CODMOT->CODMOT | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
