# TGFPPZ — Prazo por Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 1
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| PrazoPorParceiro | Prazo por parceiro | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data de alteração | H |  |  | N |
| PERCAUMENTO | % aumento | F |  |  | N |
| PRAZO | Prazo | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | PrazoPorParceiro / [TGFPPZ](TGFPPZ.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
