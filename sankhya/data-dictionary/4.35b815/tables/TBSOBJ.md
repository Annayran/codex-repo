# TBSOBJ — TABLE TBSOBJ

- Módulo técnico: [TBS](../modules/TBS.md)
- Campos: 2
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ObjetivoBSC | Objetivo BSC | S | N |
| ObjetivoBSCFilha | Objetivo BSC Filha | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| NUOBJ | Objetivo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LigacoesObjetivosBSC / [TBSOBL](TBSOBL.md) | ObjetivoBSC / [TBSOBJ](TBSOBJ.md) | NUOBJORIG->NUOBJ | I |
| LigacoesObjetivosBSC / [TBSOBL](TBSOBL.md) | ObjetivoBSCFilha / [TBSOBJ](TBSOBJ.md) | NUOBJDEST->NUOBJ | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
