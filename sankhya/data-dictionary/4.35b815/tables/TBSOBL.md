# TBSOBL — Ligação entre Objetivos Estratégicos

- Módulo técnico: [TBS](../modules/TBS.md)
- Campos: 3
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| LigacoesObjetivosBSC | Ligação entre Objetivos BSC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUOBJDEST | Objetivo Destino | I |  |  | N |
| NUOBJORIG | Objetivo Origem | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LigacoesObjetivosBSC / [TBSOBL](TBSOBL.md) | ObjetivoBSC / [TBSOBJ](TBSOBJ.md) | NUOBJORIG->NUOBJ | I |
| LigacoesObjetivosBSC / [TBSOBL](TBSOBL.md) | ObjetivoBSCFilha / [TBSOBJ](TBSOBJ.md) | NUOBJDEST->NUOBJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
