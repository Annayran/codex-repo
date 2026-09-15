# TAPEAP — Equipe por Apontamento

- Módulo técnico: [TAP](../modules/TAP.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EquipeApontamento | Equipe por Apontamento | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUAPONTA | NUAPONTA | I |  |  | N |
| NUEQUIPE | NUEQUIPE | I |  |  | N |
| NUMITEM | NUMITEM | I |  |  | N |
| SEQUENCIA | SEQUENCIA | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EquipeApontamento / [TAPEAP](TAPEAP.md) | MembroEquipe / [TCSMEQ](TCSMEQ.md) | NUEQUIPE->NUEQUIPE; SEQUENCIA->SEQUENCIA | I |
| ItemApontamento / [TAPIAP](TAPIAP.md) | EquipeApontamento / [TAPEAP](TAPEAP.md) | NUAPONTA->NUAPONTA; NUMITEM->NUMITEM | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
