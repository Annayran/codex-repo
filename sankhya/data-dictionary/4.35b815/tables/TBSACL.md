# TBSACL — TABLE TBSACL

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
| LigacaoEntreAcoesBSC | Ligação entre ações BSC | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NUACEDEST | Ação Estratégica Destino | I |  |  | N |
| NUACEORIG | Ação Estratégica Origem | I |  |  | N |
| TIPO | Tipo | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LigacaoEntreAcoesBSC / [TBSACL](TBSACL.md) | AcaoEstrategicaBSC / [TBSACE](TBSACE.md) | NUACEORIG->NUACE | I |
| LigacaoEntreAcoesBSC / [TBSACL](TBSACL.md) | AcaoEstrategicaBSCFilha / [TBSACE](TBSACE.md) | NUACEDEST->NUACE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
