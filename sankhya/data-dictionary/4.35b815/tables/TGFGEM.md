# TGFGEM — Grupo Produtos e Icms por Empresa

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| GrupoProdutoIcmsEmpresa | Grupo Produtos e Icms por Empresa | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTACTBEFD | Conta Contábil para EFD | I |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODGRUPOPROD | Grupo de produtos | I |  |  | N |
| GRUPOICMS | Grupo de ICMS | I |  |  | N |
| PERCCMTEST | % Carga Média Trib. Estadual | F |  |  | N |
| PERCCMTFED | % Carga Média Trib. Federal | F |  |  | N |
| PERCCMTIMP | % Carga Média Trib. Importação | F |  |  | N |
| PERCCMTMUN | % Carga Média Trib. Municipal | F |  |  | N |
| PERCCMTNAC | % Carga Média Trib. Nacional | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| GrupoProduto / [TGFGRU](TGFGRU.md) | GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| GrupoProdutoIcmsEmpresa / [TGFGEM](TGFGEM.md) | PlanoConta /  | CODCTACTBEFD->CODCTACTB | L |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
