# TCAREF — Referência do Período

- Módulo técnico: [TCA](../modules/TCA.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: M

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ReferenciaPeriodo | Ca_ReferenciaPeriodo | S | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODANO | Cód.Ano | I |  |  | N |
| CODREF | Cód.Referência | S |  |  | N |
| PERFIM | Fim do Período | F |  |  | N |
| PERINI | Início do Período | F |  |  | N |
| SITUACAO | Situação | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| AnoLetivo / [TCAANO](TCAANO.md) | ReferenciaPeriodo / [TCAREF](TCAREF.md) | CODANO->CODANO | I |
| ReferenciaPeriodo / [TCAREF](TCAREF.md) | AnoLetivo / [TCAANO](TCAANO.md) | CODANO->CODANO | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
