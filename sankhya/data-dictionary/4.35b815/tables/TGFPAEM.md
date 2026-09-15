# TGFPAEM — Grupo ICMS/ISS por Empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 8
- Instâncias DWF: 2
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ParceiroEmpresaTabPreco | Vendedores vinc. Tabela de Preço | N | N |
| ParceiroEmpresGrupoIcms | Grupo ICMS/ISS por Empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CLASSIFICMS | Classificação de ICMS | S |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODPARC | Cód. Parceiro | I |  |  | N |
| CODTAB | Tabela preço | I |  |  | N |
| FORMULA | Fórmula | S |  |  | N |
| GRUPOICMS | Grupo de ICMS | I |  |  | N |
| INDPRECOEMBUT | Percentual | F |  |  | N |
| RETEMISS | Retém ISS | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Parceiro / [TGFPAR](TGFPAR.md) | ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | CODPARC->CODPARC | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | EmpresaTabPreco / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | ParceiroTabPreco / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |
| ParceiroEmpresaTabPreco / [TGFPAEM](TGFPAEM.md) | TabelaPreco / [TGFTAB](TGFTAB.md) | CODTAB->CODTAB | I |
| ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |
| ParceiroEmpresGrupoIcms / [TGFPAEM](TGFPAEM.md) | Parceiro / [TGFPAR](TGFPAR.md) | CODPARC->CODPARC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
