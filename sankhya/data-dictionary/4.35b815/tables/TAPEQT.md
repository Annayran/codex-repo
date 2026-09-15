# TAPEQT — Equipamento por Apontamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipamentoApontamento | Equipamento por Apontamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | CODBEM | S |  |  | N |
| CODPROD | CODPROD | I |  |  | N |
| NUAPONTA | NUAPONTA | I |  |  | N |
| NUMITEM | NUMITEM | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipamentoApontamento / [TAPEQT](TAPEQT.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| EquipamentoApontamento / [TAPEQT](TAPEQT.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | EquipamentoApontamento / [TAPEQT](TAPEQT.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
