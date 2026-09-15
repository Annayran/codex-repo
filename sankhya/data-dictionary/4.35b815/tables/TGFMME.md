# TGFMME — Motorista MDF-e

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| MotoristaMDF | MotoristaMDF | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Motorista | I |  |  | N |
| NUVIAG | Nro. Viagem | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| MotoristaMDF / [TGFMME](TGFMME.md) | Motorista / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| MotoristaMDF / [TGFMME](TGFMME.md) | Viagem / [TGFVIAG](TGFVIAG.md) | NUVIAG->NUVIAG | I |
| Viagem / [TGFVIAG](TGFVIAG.md) | MotoristaMDF / [TGFMME](TGFMME.md) | NUVIAG->NUVIAG | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
