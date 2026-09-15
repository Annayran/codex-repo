# TGFPEIN — Empresa por Parceiro Interdependentes

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 2
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| EmpresaParceiroInterdependente | Empresa por Parceiro Interdependentes | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODEMP | Cód. Empresa | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaParceiroInterdependente / [TGFPEIN](TGFPEIN.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| EmpresaParceiroInterdependente / [TGFPEIN](TGFPEIN.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| Parceiro / [TGFPAR](TGFPAR.md) | EmpresaParceiroInterdependente / [TGFPEIN](TGFPEIN.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
