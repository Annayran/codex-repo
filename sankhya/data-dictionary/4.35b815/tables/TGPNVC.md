# TGPNVC — Níveis por Cargo

- Módulo técnico: [TGP](../modules/TGP.md)
- Campos: 6
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| NivelCargo | Níveis por Cargo | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCARGO | Cód. Cargo | I |  |  | N |
| CODNIVEL | Nível | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DHALTER | Data de Alteração | H |  |  | N |
| ORDEM | Ordem | I |  |  | N |
| POSSUIREP | Possui Representatividade | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| NivelCargo / [TGPNVC](TGPNVC.md) | Cargo /  | CODCARGO->CODCARGO | I |
| NivelCargo / [TGPNVC](TGPNVC.md) | Nivel / [TGPNIV](TGPNIV.md) | CODNIVEL->CODNIVEL | I |
| NivelCargo / [TGPNVC](TGPNVC.md) | Usuario / [TSIUSU](TSIUSU.md) | CODUSU->CODUSU | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
