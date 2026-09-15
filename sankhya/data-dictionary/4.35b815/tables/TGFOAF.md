# TGFOAF — Ocorrências por Campo

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 4
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| OcorrAlteracaoFinanceiro | Ocorrências por Campo | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| NOMECAMPO | Nome Campo | S |  |  | N |
| NUCRAF | Número Único | I |  |  | N |
| OCORRENCIA | Ocorrência | S |  |  | N |
| SEQUENCIA | Sequencia | I |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfRegAlteracaoFinanceiro / [TGFCRAF](TGFCRAF.md) | OcorrAlteracaoFinanceiro / [TGFOAF](TGFOAF.md) | NUCRAF->NUCRAF | I |
| OcorrAlteracaoFinanceiro / [TGFOAF](TGFOAF.md) | PermissoesPorOcorrencia / [TGFPPO](TGFPPO.md) | NUCRAF->NUCRAF; SEQUENCIA->SEQUENCIA | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
