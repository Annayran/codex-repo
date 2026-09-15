# TCICTA — Contas

- Módulo técnico: [TCI](../modules/TCI.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 4
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| Conta | Contas | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODBEM | Cód.Bem | S |  |  | N |
| CODCTACTB | Cód.Conta Contábil | I |  |  | N |
| CODHISTCTB | Cód.Histórico | I |  |  | N |
| CODPROD | Cód.Produto | I |  |  | N |
| TIPO | Tipo de Depreciação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| Conta / [TCICTA](TCICTA.md) | HistoricoPadrao /  | CODHISTCTB->CODHISTCTB | I |
| Conta / [TCICTA](TCICTA.md) | Imobilizado / [TCIBEM](TCIBEM.md) | CODBEM->CODBEM; CODPROD->CODPROD | I |
| Conta / [TCICTA](TCICTA.md) | PlanoConta /  | CODCTACTB->CODCTACTB | I |
| Conta / [TCICTA](TCICTA.md) | Produto / [TGFPRO](TGFPRO.md) | CODPROD->CODPROD | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
