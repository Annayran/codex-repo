# TBSACE — Ação Estratégica

- Módulo técnico: [TBS](../modules/TBS.md)
- Campos: 4
- Instâncias DWF: 2
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| AcaoEstrategicaBSC | Ação estratégica BSC | S | N |
| AcaoEstrategicaBSCFilha | Ação Estratégica BSC Filha | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| DESCRICAO | Descrição | S |  |  | N |
| DHINICIO | Data e Hora Início | H |  |  | N |
| DHLIMITE | Data e Hora Limite | H |  |  | N |
| NUACE | Ação Estratégica | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| LigacaoEntreAcoesBSC / [TBSACL](TBSACL.md) | AcaoEstrategicaBSC / [TBSACE](TBSACE.md) | NUACEORIG->NUACE | I |
| LigacaoEntreAcoesBSC / [TBSACL](TBSACL.md) | AcaoEstrategicaBSCFilha / [TBSACE](TBSACE.md) | NUACEDEST->NUACE | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
