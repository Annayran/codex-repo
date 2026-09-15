# TCCCOR — Corretores CC

- Módulo técnico: [TCC](../modules/TCC.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CorretorCivil | Corretor Construção Civil | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCC | CODCC | I |  |  | N |
| CODPARC | Parceiro | I |  |  | N |
| PERCOMISSAO | Percentual Comissão | F |  |  | N |
| VLRCOMISSAO | Vlr. Comissão | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ContratoCivil / [TCCCON](TCCCON.md) | CorretorCivil / [TCCCOR](TCCCOR.md) | CODCC->CODCC | I |
| CorretorCivil / [TCCCOR](TCCCOR.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
