# TGFDMG — Desconto Máximo Por Grupo e Tabela de Preço

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 7
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| DescontoMaxGrupoTabPreco | Desconto Máximo Por Grupo e Tabela de Preço | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| BONIFMAX | % Bonificação Máxima | F |  |  | N |
| CODEMP | Empresa | I |  |  | N |
| CODGRUPOPROD | Grupo Produto | I |  |  | N |
| CODTAB | Tabela | I |  |  | N |
| DESCMAX | % Desconto Máximo | F |  |  | N |
| DTFINAL | Data Final | D |  |  | N |
| DTINICIAL | Data Inicial | D |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | CODEMP->CODEMP | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | GrupoProduto / [TGFGRU](TGFGRU.md) | CODGRUPOPROD->CODGRUPOPROD | I |
| DescontoMaxGrupoTabPreco / [TGFDMG](TGFDMG.md) | NomeTabelasPreco / [TGFNTA](TGFNTA.md) | CODTAB->CODTAB | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
