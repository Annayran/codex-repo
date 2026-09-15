# TGFDFP — Desconto Finânceiro por Parceiro por Período

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 5
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoFinanceiroParcPeriodo | Desconto Financeiro por Parceiro/Período | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDESC | Desconto | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODPARCMATRIZ | Cód. Parceiro Matriz | I |  |  | S |
| CODUSU | Cód. Usuário | I |  |  | N |
| DTALTER | Data de Alteração | H |  |  | N |
| DTFIM | Data Fim | D |  |  | N |
| DTINICIO | Data Início | D |  |  | N |
| PERCDESC | Percentual de Desconto | F |  |  | N |
| VLRDESC | Valor de Desconto | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | DescontoFinanceiroParceiro / [TGFDFI](TGFDFI.md) | CODDESC->CODDESC | I |
| DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | ParceiroMatriz / [TGFPAR](TGFPAR.md) | CODPARCMATRIZ->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | DescontoFinanceiroParcPeriodo / [TGFDFP](TGFDFP.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
