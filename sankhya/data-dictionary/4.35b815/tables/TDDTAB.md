# TDDTAB — Descrição de Tabelas

- Módulo técnico: [TDD](../modules/TDD.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Tabela | Descrição da Tabela | S | S |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| ADICIONAL | Adicional | S |  |  | N |
| DESCRTAB | Descrição da Tabela | S |  |  | N |
| NOMETAB | Nome da Tabela | S |  |  | N |
| NUCAMPONUMERACAO | Cód. Campo Numeração | I |  |  | N |
| TIPONUMERACAO | Tipo de Numeração | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| FormularioFormatado / [TDDINS](TDDINS.md) | Tabela / [TDDTAB](TDDTAB.md) | NOMETAB->NOMETAB | I |
| Instancia / [TDDINS](TDDINS.md) | Tabela / [TDDTAB](TDDTAB.md) | NOMETAB->NOMETAB | I |
| RegrasCustomizadas / [TSIRGB](TSIRGB.md) | Tabela / [TDDTAB](TDDTAB.md) | TABELA->NOMETAB | I |
| Tabela / [TDDTAB](TDDTAB.md) | Campo / [TDDCAM](TDDCAM.md) | NOMETAB->NOMETAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
