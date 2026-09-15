# TGFFOR — Fórmulas de Precificação

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 14
- Instâncias DWF: 1
- Relações DWF: 3
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| FormulaPrecificacao | Fórmula de Precificação | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODFORMPREC | Código | I |  |  | N |
| DESCRCUSCOMICMS | Descrição | S |  |  | N |
| DESCRCUSREP | Descrição | S |  |  | N |
| DESCRCUSSEMICMS | Descrição | S |  |  | N |
| DESCRCUSVARIAVEL | Descrição | S |  |  | N |
| DESCRFORM | Descrição | S |  |  | N |
| DESCRFORMULA | Descrição | S |  |  | N |
| DESCRPRECOTAB | Descrição | S |  |  | N |
| FORCUSCOMICMS | Fórmula Custo Com ICMS | S |  |  | N |
| FORCUSREP | Fórmula para Custo Reposição | S |  |  | N |
| FORCUSSEMICMS | Fórmula Custo Sem ICMS | S |  |  | N |
| FORCUSVARIAVEL | Fórmula Custo Variável | S |  |  | N |
| FORMULA | Fórmula Custo Gerencial | S |  |  | N |
| FORPRECOTAB | Fórmula para o preço de tabela | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| EmpresaFinanceiro / [TGFEMP](TGFEMP.md) | FormulaPrecificacao / [TGFFOR](TGFFOR.md) | CODFORMPREC->CODFORMPREC | I |
| Produto / [TGFPRO](TGFPRO.md) | FormulaPrecificacao / [TGFFOR](TGFFOR.md) | CODFORMPREC->CODFORMPREC | I |
| Servico / [TGFPRO](TGFPRO.md) | FormulaPrecificacao / [TGFFOR](TGFFOR.md) | CODFORMPREC->CODFORMPREC | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
