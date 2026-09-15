# TGFDFD — Dependentes de Financeiro Dedução

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
| DepenFinancDeducao | Dependentes de Financeiro Dedução | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODDEPEND | Código Dependente | I |  |  | N |
| CODPARC | Código Parceiro | I |  |  | N |
| NUFIN | Nro Único | I |  |  | N |
| TPDEDUCAO | Tipo Dedução | S |  |  | N |
| VLRDEDUCAO | Valor de Dedução | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DepenFinancDeducao / [TGFDFD](TGFDFD.md) | DependenteIR / [TGFDPP](TGFDPP.md) | CODDEPEND->CODDEPEND; CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
