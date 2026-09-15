# TGFLCP — Limite de Crédito por Parceiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| CreditoPorParceiro | Limite de Crédito p/ Grupo Tip. Título | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data Alteração | H |  |  | N |
| GRUPOLIMCRED | Grupo Limite de Crédito | S |  |  | N |
| LIMCRED | Limite de Crédito | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| CreditoPorParceiro / [TGFLCP](TGFLCP.md) | TipoTitulo / [TGFTIT](TGFTIT.md) | GRUPOLIMCRED->GRUPOLIMCRED | I |
| Parceiro / [TGFPAR](TGFPAR.md) | CreditoPorParceiro / [TGFLCP](TGFLCP.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
