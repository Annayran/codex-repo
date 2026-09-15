# TGFCRAF — Config. Reg. de Alteração Financeiro

- Módulo técnico: [TGF](../modules/TGF.md)
- Campos: 5
- Instâncias DWF: 1
- Relações DWF: 2
- Objeto adicional: não indicado
- Tipo de numeração: A

> As relações documentadas são relações de instância do DWF e não comprovam, isoladamente, uma foreign key física no banco.

## Instâncias

| Instância | Descrição | Ativa | Raiz |
|---|---|---:|---:|
| ConfRegAlteracaoFinanceiro | Config. Reg. de Alteração Financeiro | N | N |

## Campos

| Campo | Descrição | Tipo | Tamanho | Adicional | Calculado |
|---|---|---|---:|---:|---:|
| CODCTABCOINT | Conta Bancária | I |  |  | N |
| NUCRAF | Número Único | I |  |  | N |
| OCORRENCIA_BAIXA | Ocorrência de Baixa | S |  |  | N |
| OCORRENCIA_ENTRADA | Ocorrência de Entrada | S |  |  | N |
| OCORRENCIA_EXCLUSAO | Ocorrência de Exclusão | S |  |  | N |

## Relações DWF

| Origem | Destino | Campos | Tipo |
|---|---|---|---|
| ConfRegAlteracaoFinanceiro / [TGFCRAF](TGFCRAF.md) | ContaBancaria / [TSICTA](TSICTA.md) | CODCTABCOINT->CODCTABCOINT | I |
| ConfRegAlteracaoFinanceiro / [TGFCRAF](TGFCRAF.md) | OcorrAlteracaoFinanceiro / [TGFOAF](TGFOAF.md) | NUCRAF->NUCRAF | I |

[Voltar ao índice de tabelas](../TABLE_INDEX.md)
