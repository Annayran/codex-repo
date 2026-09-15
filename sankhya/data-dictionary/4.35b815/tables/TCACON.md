# TCACON — Controles

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 9
- Instâncias DWF: 1
- Relações DWF: 9
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Controle | Controles | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODCON | Cód.Controle | S |  |  | N |
| CODEMP | Cód.Empresa | I |  |  | N |
| FLGABXMED | FLGABXMED | S |  |  | N |
| NOMCON | Nome do Controle | S |  |  | N |
| ORDCON | Ordem do Controle | I |  |  | N |
| SITCON | Situação do Controle | S |  |  | N |
| VLRMAX | Valor Máximo | F |  |  | N |
| VLRMIN | Valor Mínimo | F |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | Controle / [TCACON](TCACON.md) | CODANO->CODANO | I |
| Controle / [TCACON](TCACON.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |
| Controle / [TCACON](TCACON.md) | Empresa / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaCadastro / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaContablidade / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaSoma / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | EmpresaSubstituido / [TSIEMP](TSIEMP.md) | CODEMP->CODEMP | I |
| Controle / [TCACON](TCACON.md) | MapaDiario / [TCAMAP](TCAMAP.md) | CODANO->CODANO; CODCON->CONCEITO | I |
| MapaDiario / [TCAMAP](TCAMAP.md) | Controle / [TCACON](TCACON.md) | CODANO->CODANO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
